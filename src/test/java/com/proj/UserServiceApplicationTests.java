package com.proj;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proj.jwt.JwtUtil;
import com.proj.model.User;
import com.proj.repository.SalonServicesrepository;
import com.proj.repository.Userrepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Userrepository userrepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private SalonServicesrepository salonServicesrepository;

	@Test
	void contextLoads() {
	}

	@Test
	void addSalonAddServicesAndAddSalonServicesuiFlowWorks() throws Exception {
		User salonOwner = new User();
		salonOwner.setFullname("Salon Owner");
		salonOwner.setEmailid("salon-owner-" + UUID.randomUUID() + "@example.com");
		salonOwner.setPassword(passwordEncoder.encode("password123"));
		salonOwner.setPhone("9876543210");
		salonOwner.setRole(List.of("ROLE_SALON"));
		salonOwner = userrepository.save(salonOwner);

		String token = jwtUtil.generateToken(salonOwner);

		MvcResult salonResult = mockMvc.perform(post("/salon/addsalon")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "name":"Glow Studio",
					  "email":"glowstudio@example.com",
					  "phone":"9123456789",
					  "location":"Bhubaneswar",
					  "openingtime":"09:00:00",
					  "closingtime":"19:00:00",
					  "imageUrl":"/uploads/test-salon.jpg"
					}
					"""))
				.andExpect(status().isOk())
				.andReturn();

		Map<String, Object> salonResponse = objectMapper.readValue(
				salonResult.getResponse().getContentAsString(),
				new TypeReference<Map<String, Object>>() {
				});
		Integer salonId = (Integer) salonResponse.get("id");
		assertThat(salonId).isNotNull();

		MvcResult serviceResult = mockMvc.perform(post("/salon/addservices")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "name":"Hair Spa"
					}
					"""))
				.andExpect(status().isOk())
				.andReturn();

		Map<String, Object> serviceResponse = objectMapper.readValue(
				serviceResult.getResponse().getContentAsString(),
				new TypeReference<Map<String, Object>>() {
				});
		Integer serviceId = (Integer) serviceResponse.get("id");
		assertThat(serviceId).isNotNull();

		mockMvc.perform(post("/salon/addsalonservicesui")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(Map.of(
						"salonId", salonId,
						"serviceId", serviceId,
						"cost", 499.0,
						"imageUrl", "/uploads/test-service.jpg"))))
				.andExpect(status().isOk());

		assertThat(salonServicesrepository.findByServiceId(serviceId.longValue()))
				.isNotEmpty()
				.anySatisfy(ss -> {
					assertThat(ss.getSalon()).isNotNull();
					assertThat(ss.getSalon().getId()).isEqualTo(salonId.longValue());
					assertThat(ss.getServices()).isNotNull();
					assertThat(ss.getServices().getId()).isEqualTo(serviceId.longValue());
					assertThat(ss.getCost()).isEqualTo(499.0);
				});
	}
}
