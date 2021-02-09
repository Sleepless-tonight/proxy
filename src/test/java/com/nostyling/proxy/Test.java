// package com.nostyling.proxy;
//
// import com.jdcloud.sdk.auth.sign.Signer;
// import com.jdcloud.apigateway.signature.algorithm.ISignAlgorithm;
// import com.jdcloud.apigateway.signature.algorithm.ISignAlgorithm.ApiGatewayAlgorithm;
// import com.jdcloud.apigateway.signature.algorithm.ISignAlgorithm.StaticSignAlgorithm;
//
// import java.util.HashMap;
// import java.util.Map;
//
// /**
//  * @author shiliang
//  * @Classname Test
//  * @Date 2021/1/12 15:35
//  * @Description TODO
//  */
// public class Test {
//     public static void main(String[] args) {
//         String secretKey = "secret key";
//         String host = "192.168.182.82:5888";
//         String uri = "/test/httpCode";
//         String method = "GET";
//         String queryString = "code=200";
//         String body = "this is body";
//         Map<String, String> headers = new HashMap<>();
//         headers.put("x-jdcloud-nonce", "1263de8a-9adf-4626-ade0-f25b608d3a55");
//         headers.put("x-jdcloud-date", "20181114T085919Z");
//         headers.put("authorization", "JDCLOUD2-HMAC-SHA256 Credential=636B856DCF14D467D313CCB0C0E2B21C/20181114/cn-north-1/xapw9ul97y4p/jdcloud2_request, SignedHeaders=content-type;x-jdcloud-date;x-jdcloud-nonce, Signature=9b085f63d7fd1583f4fe20356c5770da9dffb51ea66fcdbd45e8a0c8b0a7dbfa");
//         headers.put("content-type", "application/json");
//         Signer signer = new Signer(ApiGatewayAlgorithm.SHA256);
//         String signature = signer.sign(secretKey, host, uri, method, headers, queryString, body);
//         System.out.println(signature);
//     }
// }
