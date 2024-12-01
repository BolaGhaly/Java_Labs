package Controller;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/", ctx -> ctx.result("Hello world"));
        app.post("/register", this::postUserAccount);
        app.post("/login", this::postUserLogin);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserId);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByMessageId);
        app.post("/messages", this::postNewMessage);
        app.patch("/messages/{message_id}", this::updateExistingMessage);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageId);
        return app;
    }

    private void postUserAccount(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedUserAccount = accountService.createNewUser(account);
        if (addedUserAccount != null) {
            ctx.json(mapper.writeValueAsString(addedUserAccount)).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void postUserLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account userAccountLogin = accountService.userLogin(account);
        if (userAccountLogin != null) {
            ctx.json(mapper.writeValueAsString(userAccountLogin)).status(200);
        } else {
            ctx.status(401);
        }
    }

    private void getAllMessages(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> allMessages = messageService.getAllMessages();
        ctx.json(mapper.writeValueAsString(allMessages)).status(200);
    }

    private void getMessageByMessageId(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageByMessageId = messageService.getMessageById(Integer.parseInt((ctx.pathParam("message_id"))));
        if (messageByMessageId != null) {
            ctx.json(mapper.writeValueAsString(messageByMessageId)).status(200);
        } else {
            ctx.status(200);
        }
    }

    private void getAllMessagesByUserId(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> allMessagesByAccountId = messageService
                .getAllMessagesByAccountId(Integer.parseInt((ctx.pathParam("account_id"))));
        ctx.json(mapper.writeValueAsString(allMessagesByAccountId)).status(200);
    }

    private void postNewMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdNewMessage = messageService.createNewMessage(message);
        if (createdNewMessage != null) {
            ctx.json(mapper.writeValueAsString(createdNewMessage)).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void updateExistingMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Integer message_id = Integer.parseInt((ctx.pathParam("message_id")));
        Message updatedExistingMessage = messageService.updateMessageById(message_id, message.getMessage_text());
        if (updatedExistingMessage != null) {
            ctx.json(mapper.writeValueAsString(updatedExistingMessage)).status(200);
        } else {
            ctx.status(400);
        }
    }

    private void deleteMessageByMessageId(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Integer message_id = Integer.parseInt((ctx.pathParam("message_id")));
        Message deletedMessageById = messageService.deleteMessageById(message_id);
        if (deletedMessageById != null) {
            ctx.json(mapper.writeValueAsString(deletedMessageById)).status(200);
        } else {
            ctx.status(200);
        }
    }
}