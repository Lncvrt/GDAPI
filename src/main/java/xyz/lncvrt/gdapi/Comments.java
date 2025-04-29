package xyz.lncvrt.gdapi;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import xyz.lncvrt.gdapi.exception.InvalidResponseException;
import xyz.lncvrt.gdapi.exception.RequestFailedException;
import xyz.lncvrt.gdapi.exception.UnknownErrorException;
import xyz.lncvrt.gdapi.util.GDEncryption;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SuppressWarnings("unused")
public class Comments {
    private final GDAPI gdapi;

    public Comments(GDAPI gdapi) {
        this.gdapi = gdapi;
    }

    public int uploadLevelComment(String comment, int levelID, int completionPercent) throws UnknownErrorException, InvalidResponseException, RequestFailedException {
        String encodedComment = Base64.getEncoder().encodeToString(comment.getBytes(StandardCharsets.UTF_8));
        List<Object> chkValues = new ArrayList<>();
        chkValues.add(gdapi.accountName);
        chkValues.add(encodedComment);
        chkValues.add(levelID);
        chkValues.add(completionPercent);
        String chk = GDEncryption.generateChk(chkValues, "29481", "0xPT6iUrtws0J");

        RequestBody formBody = new FormBody.Builder()
                .add("accountID", String.valueOf(gdapi.accountID))
                .add("gjp2", GDEncryption.generateGJP2(gdapi.accountPassword))
                .add("userName", gdapi.accountName)
                .add("comment", encodedComment)
                .add("secret", "Wmfd2893gb7")
                .add("levelID", String.valueOf(levelID))
                .add("percent", String.valueOf(completionPercent))
                .add("chk", chk)
                .build();

        Request request = new Request.Builder()
                .url(gdapi.baseUrl + "uploadGJComment21.php")
                .post(formBody)
                .addHeader("User-Agent", " ")
                .build();

        try (Response response = GDAPI.httpClient.newCall(request).execute()) {
            String body = response.body().string();
            if (body.equals("-1")) {
                throw new UnknownErrorException();
            } else {
                try {
                    return Integer.parseInt(body);
                } catch (NumberFormatException e) {
                    throw new InvalidResponseException();
                }
            }
        } catch (RuntimeException | IOException e) {
            throw new RequestFailedException();
        }
    }

    public int uploadAccountComment(String comment) throws UnknownErrorException, InvalidResponseException, RequestFailedException {
        String encodedComment = Base64.getEncoder().encodeToString(comment.getBytes(StandardCharsets.UTF_8));

        RequestBody formBody = new FormBody.Builder()
                .add("accountID", String.valueOf(gdapi.accountID))
                .add("gjp2", GDEncryption.generateGJP2(gdapi.accountPassword))
                .add("comment", encodedComment)
                .add("secret", "Wmfd2893gb7")
                .build();

        Request request = new Request.Builder()
                .url(gdapi.baseUrl + "uploadGJAccComment20.php")
                .post(formBody)
                .addHeader("User-Agent", " ")
                .build();

        try (Response response = GDAPI.httpClient.newCall(request).execute()) {
            String body = response.body().string();
            if (body.equals("-1")) {
                throw new UnknownErrorException();
            } else {
                try {
                    return Integer.parseInt(body);
                } catch (NumberFormatException e) {
                    throw new InvalidResponseException();
                }
            }
        } catch (RuntimeException | IOException e) {
            throw new RequestFailedException();
        }
    }

    public boolean deleteLevelComment(int commentID, int levelID) throws RequestFailedException {
        RequestBody formBody = new FormBody.Builder()
                .add("accountID", String.valueOf(gdapi.accountID))
                .add("gjp2", GDEncryption.generateGJP2(gdapi.accountPassword))
                .add("commentID", String.valueOf(commentID))
                .add("levelID", String.valueOf(levelID))
                .add("secret", "Wmfd2893gb7")
                .build();

        Request request = new Request.Builder()
                .url(gdapi.baseUrl + "deleteGJComment20.php")
                .post(formBody)
                .addHeader("User-Agent", " ")
                .build();

        try (Response response = GDAPI.httpClient.newCall(request).execute()) {
            return response.body().string().equals("1");
        } catch (RuntimeException | IOException e) {
            throw new RequestFailedException();
        }
    }

    public boolean deleteAccountComment(int commentID, int targetAccountId) throws RequestFailedException {
        RequestBody formBody = new FormBody.Builder()
                .add("accountID", String.valueOf(gdapi.accountID))
                .add("gjp2", GDEncryption.generateGJP2(gdapi.accountPassword))
                .add("commentID", String.valueOf(commentID))
                .add("secret", "Wmfd2893gb7")
                .add("targetAccountID", String.valueOf(targetAccountId))
                .build();

        Request request = new Request.Builder()
                .url(gdapi.baseUrl + "deleteGJAccComment20.php")
                .post(formBody)
                .addHeader("User-Agent", " ")
                .build();

        try (Response response = GDAPI.httpClient.newCall(request).execute()) {
            return response.body().string().equals("1");
        } catch (RuntimeException | IOException e) {
            throw new RequestFailedException();
        }
    }

    public boolean deleteAccountComment(int commentID) throws RequestFailedException {
        return deleteAccountComment(commentID, gdapi.accountID);
    }
}
