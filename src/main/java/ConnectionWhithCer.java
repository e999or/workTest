import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import sun.net.www.http.HttpClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

public class ConnectionWhithCer {
    String s;
    String username;
    String password;
    String cer;

    public String connectionToUrl (String url) throws IOException, URISyntaxException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        System.out.println(url);
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

//        try {
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        } catch (Exception e) {
//
//        }

        String keyPassphrase = "";
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(cer), keyPassphrase.toCharArray());
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, null)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
        HttpResponse response = httpClient.execute(new HttpGet(url));


        String userPass = username + ":" + password;
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes("UTF-8"));
        //URLConnection connection = response.openConnection();
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) response;
        httpsURLConnection.setRequestProperty ("Authorization", basicAuth);

        InputStream is = httpsURLConnection.getInputStream();
        InputStreamReader reader = new InputStreamReader(is);

        char[] buffer = new char[256];
        int rc;
        StringBuilder sb = new StringBuilder();
        while ((rc = reader.read(buffer)) != -1)
            sb.append(buffer, 0, rc);
        reader.close();
        s = String.valueOf(sb);


        return s;
    }
}

