package com.example.app;

import com.example.app.securityscope.SecurityScope;
import com.example.app.securityscope.SecurityScopeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityScopeRepository securityScopeRepository;

    @BeforeEach
    void setup() {
        securityScopeRepository.deleteAll();
    }

    @Test
    void loginReturnsToken() throws Exception {
        String body = "{\"username\":\"admin\",\"password\":\"password\"}";
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk());
    }

    @Test
    void menuRespectsPermissions() throws Exception {
        String token = login("userA");
        MvcResult result = mockMvc.perform(get("/api/me/menu")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Painel A");
        assertThat(content).doesNotContain("Painel B");
    }

    @Test
    void embedForbiddenWithoutPermission() throws Exception {
        String token = login("userA");
        mockMvc.perform(post("/api/reports/2/embed")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void embedCreatesScopeForUserPolicy() throws Exception {
        String token = login("userB");
        mockMvc.perform(post("/api/reports/1/embed")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
        List<SecurityScope> scopes = securityScopeRepository.findByPrincipalAndReportKey("userB", "1");
        assertThat(scopes).extracting(SecurityScope::getDimensionKey)
            .containsExactlyInAnyOrder("orgao_id", "unidade_id", "tipo_despesa_id");
        assertThat(scopes).filteredOn(scope -> scope.getDimensionKey().equals("tipo_despesa_id"))
            .extracting(SecurityScope::getDimensionValue)
            .containsExactly("X");
    }

    @Test
    void embedCreatesScopeForAllowAll() throws Exception {
        String token = login("userA");
        mockMvc.perform(post("/api/reports/1/embed")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
        List<SecurityScope> scopes = securityScopeRepository.findByPrincipalAndReportKey("userA", "1");
        assertThat(scopes).filteredOn(scope -> scope.getDimensionKey().equals("unidade_id"))
            .extracting(SecurityScope::getDimensionValue)
            .containsExactly("*");
        assertThat(scopes).filteredOn(scope -> scope.getDimensionKey().equals("tipo_despesa_id"))
            .extracting(SecurityScope::getDimensionValue)
            .containsExactly("*");
    }

    @Test
    void userPolicyOverridesGroupPolicy() throws Exception {
        String token = login("userB");
        mockMvc.perform(post("/api/reports/1/embed")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
        List<SecurityScope> scopes = securityScopeRepository.findByPrincipalAndReportKey("userB", "1");
        assertThat(scopes).filteredOn(scope -> scope.getDimensionKey().equals("tipo_despesa_id"))
            .extracting(SecurityScope::getDimensionValue)
            .containsExactly("X");
    }

    private String login(String username) throws Exception {
        String body = objectMapper.writeValueAsString(new LoginPayload(username, "password"));
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andReturn();
        String response = result.getResponse().getContentAsString();
        return objectMapper.readTree(response).get("accessToken").asText();
    }

    private record LoginPayload(String username, String password) {
    }
}
