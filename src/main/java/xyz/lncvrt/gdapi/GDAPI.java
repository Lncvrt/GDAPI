package xyz.lncvrt.gdapi;

import okhttp3.*;
import xyz.lncvrt.gdapi.exception.*;
import xyz.lncvrt.gdapi.util.GDEncryption;

import java.io.IOException;

@SuppressWarnings("unused")
public class GDAPI {
    static final OkHttpClient httpClient = new OkHttpClient();
    final String accountName;
    final String accountPassword;
    final Integer accountID;
    final Integer playerID;
    final String udid;
    final String baseUrl;

    public GDAPI(String accountName, String accountPassword, String baseUrl) throws UnknownErrorException, PasswordTooShortException, UsernameTooShortException, IncorrectLoginException, AccountDisabledException, RequestFailedException {
        this.accountName = accountName;
        this.accountPassword = accountPassword;
        this.baseUrl = baseUrl;
        this.udid = GDEncryption.generateUdid();

        RequestBody formBody = new FormBody.Builder()
                .add("udid", udid)
                .add("userName", accountName)
                .add("gjp2", GDEncryption.generateGJP2(accountPassword))
                .add("secret", "Wmfv3899gc9")
                .build();

        Request request = new Request.Builder()
                .url(baseUrl + "accounts/loginGJAccount.php")
                .post(formBody)
                .addHeader("User-Agent", " ")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String body = response.body().string();
            switch (body) {
                case "-1":
                    throw new UnknownErrorException();
                case "-8":
                    throw new PasswordTooShortException();
                case "-9":
                    throw new UsernameTooShortException();
                case "-11":
                    throw new IncorrectLoginException();
                case "-12":
                    throw new AccountDisabledException();
                default:
                    String[] split = body.split(",");
                    try {
                        this.accountID = Integer.valueOf(split[0]);
                        this.playerID = Integer.valueOf(split[1]);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
            }
        } catch (RuntimeException | IOException e) {
            throw new RequestFailedException();
        }
    }

    public GDAPI(String accountName, String accountPassword) throws UnknownErrorException, RequestFailedException, IncorrectLoginException, PasswordTooShortException, UsernameTooShortException, AccountDisabledException {
        this(accountName, accountPassword, "https://www.boomlings.com/database/");
    }
}