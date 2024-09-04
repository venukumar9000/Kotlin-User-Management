//package com.motivity.mcf_kotlin_user_management
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.github.tomakehurst.wiremock.WireMockServer
//import com.github.tomakehurst.wiremock.client.WireMock.*
//import com.github.tomakehurst.wiremock.stubbing.AbstractStubMappings
//import com.github.tomakehurst.wiremock.stubbing.StubMapping
//import com.motivity.mcf_kotlin_user_management.models.LoginResponse
//import com.motivity.mcf_kotlin_user_management.models.UserLogin
//
//class MockBackendAPI {
//    companion object{
//        private val wireMockServer = WireMockServer(8080);
//
//        val mapper: ObjectMapper = ObjectMapper().apply {  }
//
//        @JvmStatic
//        fun start(){
//            wireMockServer.start();
//        }
//
//        @JvmStatic
//        fun stop(){
//            wireMockServer.stop();
//        }
//
//        @JvmStatic
//        fun remove(stubMapping: StubMapping){
//            wireMockServer.removeStub(stubMapping);
//        }
//
//        @JvmStatic
//        fun mockLoginSuccessWithSuccess(): StubMapping{
//            val ul = UserLogin("xyz@gmail.com", "****");
//            val response = LoginResponse(
//                "accessToken",
//                "xyz@gmail.com",
//                "refreshToken",
//                "test user"
//            )
//            return stubFor(
//                post(urlPathEqualTo("/api/login"))
//                    .withRequestBody(equalToJson(mapper.writeValueAsString(ul)))
//                    .willReturn(aResponse().withBody(mapper.writeValueAsString(response)).withStatus(200))
//            )
//        }
//    }
//
//
//
//
//}