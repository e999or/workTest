
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Base64;


public class ConnectionTo {

    String s;
    String username;
    String password;


    public String connectionToUrl (String url) throws IOException, URISyntaxException {
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

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {

        }

        try {
            String userPass = username + ":" + password;
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userPass.getBytes("UTF-8"));
            URLConnection connection = new URL(url).openConnection();
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) connection;
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return s;
    }
}
