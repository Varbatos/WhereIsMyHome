package com.ssafy.chat.chatservice;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // 질문에 대한 응답을 생성하는 로직
    public String getResponse(String question) {
        System.out.println("질문내용 : " + question);
        String command = question;
        PromptTemplate template = new PromptTemplate(command);
        String message = template.render();
        Message userMessage = new UserMessage(message);
        
        // 프롬프트를 시스템 메시지와 함께 처리
        Message systemMessage = new SystemMessage("");
        String response = chatModel.call(userMessage, systemMessage);
        System.out.println("response = " + response);
        return response;
    }
}


///저번 내용을 기억함
//package com.ssafy.chat.chatservice;
//
//import org.springframework.ai.chat.messages.Message;
//import org.springframework.ai.chat.messages.SystemMessage;
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.ai.chat.prompt.PromptTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ChatService {
//
//    private final ChatModel chatModel;
//    private StringBuilder conversationHistory;
//
//    public ChatService(ChatModel chatModel) {
//        this.chatModel = chatModel;
//        this.conversationHistory = new StringBuilder();  // 대화 기록 초기화
//    }
//
//    // 질문에 대한 응답을 생성하는 로직
//    public String getResponse(String question) {
//        // 대화 기록에 질문을 추가
//        conversationHistory.append("User: ").append(question).append("\n");
//
//        // 대화 기록을 프롬프트로 사용
//        String command = conversationHistory.toString();
//        PromptTemplate template = new PromptTemplate(command);
//        String message = template.render();
//        Message userMessage = new UserMessage(message);
//        
//        // 시스템 메시지와 함께 처리
//        Message systemMessage = new SystemMessage(""); // 필요시 시스템 메시지 추가
//        String response = chatModel.call(userMessage, systemMessage);
//
//        // 대화 기록에 GPT의 응답을 추가
//        conversationHistory.append("GPT: ").append(response).append("\n");
//
//        System.out.println("response = " + response);
//        return response;
//    }
//}
