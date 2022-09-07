package com.factory.embea;

import com.factory.embea.model.request.InsuredPerson;
import com.factory.embea.model.request.InsuredPersonWithId;
import com.factory.embea.model.request.PolicyCreationRequest;
import com.factory.embea.model.response.PolicyCreationResponse;
import com.factory.embea.service.PolicyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PolicyControllerValidationTest {
    @MockBean
    private PolicyService policyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /events/{id} Test - Success")
    public void getEventByIdSuccess() throws Exception {
        //given

        var policyCreationRequest = new PolicyCreationRequest("01.01.2000", List.of(new InsuredPerson("AAA", "BBB", 10.2d)));
        given(policyService.createPolicy(any())).willReturn(new PolicyCreationResponse(
                "111", "01.01.2000",
                List.of(new InsuredPersonWithId(1L, "AAA", "BBB", 10.2)),
                10.2d));
        //when

        mockMvc.perform(post("/", "id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(policyCreationRequest)))
                //then
                .andExpect(status().isBadRequest());
                //.andExpect(jsonPath("$.name").value("testEvent"));


    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
