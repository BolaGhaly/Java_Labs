package Service;

import Model.Message;

import java.util.List;

import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(Integer id) {
        return messageDAO.getMessageById(id);
    }

    public List<Message> getAllMessagesByAccountId(Integer id) {
        return messageDAO.getAllMessagesByAccountId(id);
    }

    public Message createNewMessage(Message message) {
        return messageDAO.createNewMessage(message);
    }

    public Message updateMessageById(Integer messageId, String newMessageText) {
        return messageDAO.updateMessageById(messageId, newMessageText);
    }

    public Message deleteMessageById(Integer id) {
        return messageDAO.deleteMessageById(id);
    }
}
