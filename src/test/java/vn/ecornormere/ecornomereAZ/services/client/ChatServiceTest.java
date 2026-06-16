package vn.ecornormere.ecornomereAZ.services.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import vn.ecornomere.ecornomereAZ.dto.response.ChatMessageDto;
import vn.ecornomere.ecornomereAZ.exception.ChatHistoryException;
import vn.ecornomere.ecornomereAZ.model.entity.ChatMessage;
import vn.ecornomere.ecornomereAZ.model.entity.Product;
import vn.ecornomere.ecornomereAZ.model.entity.User;
import vn.ecornomere.ecornomereAZ.repository.ChatMessageRepository;
import vn.ecornomere.ecornomereAZ.service.ChatBoxAi.ChatService;
import vn.ecornomere.ecornomereAZ.service.ProductService;
import vn.ecornomere.ecornomereAZ.service.UserService;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {
    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChatService chatService;

    private User user;


    @BeforeEach
    void setup() {

        user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
    }
    @Test
    void shouldReturnHistoryByUser_WhenUserExists() {

        ChatMessage message = new ChatMessage();
        message.setMessage("Laptop Dell");
        message.setResponse("[OK] Dell XPS");

        when(chatMessageRepository.findByUserOrderByCreatedAtAsc(user))
                .thenReturn(List.of(message));

        List<ChatMessageDto> result =
                chatService.getChatHistory("session1", user);

        assertEquals(1, result.size());

        verify(chatMessageRepository)
                .findByUserOrderByCreatedAtAsc(user);
    }
    @Test
    void shouldReturnHistoryBySession_WhenUserIsNull() {

        ChatMessage message = new ChatMessage();
        message.setMessage("Asus");
        message.setResponse("[OK] Asus TUF");

        when(chatMessageRepository
                .findBySessionIdOrderByCreatedAtAsc("session1"))
                .thenReturn(List.of(message));

        List<ChatMessageDto> result =
                chatService.getChatHistory("session1", null);

        assertEquals(1, result.size());

        verify(chatMessageRepository)
                .findBySessionIdOrderByCreatedAtAsc("session1");
    }
    @Test
    void shouldThrowChatHistoryException_WhenRepositoryFails() {

        when(chatMessageRepository.findByUserOrderByCreatedAtAsc(user)).thenThrow(new RuntimeException("DB Error"));
        assertThrows(
                ChatHistoryException.class,
                () -> chatService.getChatHistory("session1", user)
        );
    }
    @Test
    void shouldSaveMessageSuccessfully() {
        chatService.saveMessage(
                "session1",
                user,
                "Laptop gaming",
                "[OK] Acer Nitro"
        );
        ArgumentCaptor<ChatMessage> captor = ArgumentCaptor.forClass(ChatMessage.class);

        verify(chatMessageRepository).save(captor.capture());

        ChatMessage saved = captor.getValue();

        assertEquals("session1", saved.getSessionId());
        assertEquals(user, saved.getUser());
        assertEquals("Laptop gaming", saved.getMessage());
    }
    @Test
    void shouldDeleteHistoryByUser() {

        chatService.clearChatHistory("session1", user);

        verify(chatMessageRepository)
                .deleteByUser(user);
    }
    @Test
    void shouldDeleteHistoryBySession() {

        chatService.clearChatHistory("session1", null);

        verify(chatMessageRepository)
                .deleteBySessionId("session1");
    }
    @Test
    void shouldThrowRuntimeException_WhenDeleteFails() {

        doThrow(new RuntimeException("DB Error"))
                .when(chatMessageRepository)
                .deleteByUser(user);

        assertThrows(
                RuntimeException.class,
                () -> chatService.clearChatHistory("session1", user)
        );
    }
    @Test
    void shouldBuildProductContextCorrectly() {

        Product product = new Product();

        product.setName("Dell XPS");
        product.setPrice(30000000);
        product.setQuantity(5);
        product.setFactory("Dell");
        product.setShortDesc("Laptop cao cấp");
        product.setDetailDesc("Core Ultra 7");

        String result = ReflectionTestUtils.invokeMethod(
                        chatService,
                        "buildProductContext",
                        List.of(product)
                );
        assertThat(result)
                .contains("Dell XPS")
                .contains("30000000")
                .contains("Dell")
                .contains("Laptop cao cấp")
                .contains("Core Ultra 7");
    }
    @Test
    void shouldReturnEmpty_WhenProductListEmpty() {

        String result =
                ReflectionTestUtils.invokeMethod(
                        chatService,
                        "buildProductContext",
                        Collections.emptyList()
                );

        assertEquals("", result);
    }
    @Test
    void shouldReturnTrue_WhenResponseIsInvalid() {

        Boolean result =
                ReflectionTestUtils.invokeMethod(
                        chatService,
                        "isInvalidResponse",
                        "[INVALID]"
                );

        assertTrue(result);
    }
    @Test
    void shouldReturnFalse_WhenResponseIsNotInvalid() {

        Boolean result =
                ReflectionTestUtils.invokeMethod(
                        chatService,
                        "isInvalidResponse",
                        "[OK] Hello"
                );

        assertFalse(result);
    }
    @Test
    void shouldReturnTrue_WhenResponseIsNotFound() {

        Boolean result =
                ReflectionTestUtils.invokeMethod(
                        chatService,
                        "isNotFoundResponse",
                        "[NOT_FOUND]"
                );

        assertTrue(result);
    }
    @Test
    void shouldExtractOkContent() {

        String result =
                ReflectionTestUtils.invokeMethod(
                        chatService,
                        "extractOkContent",
                        "[OK] Dell XPS 15"
                );

        assertEquals("Dell XPS 15", result);
    }
    @Test
    void shouldRotateApiKeysRoundRobin() {

        List<String> keys = List.of("KEY1", "KEY2", "KEY3");

        String k1 = ReflectionTestUtils.invokeMethod(
                        chatService,
                        "getNextApiKey",
                        keys
                );

        String k2 = ReflectionTestUtils.invokeMethod(
                        chatService,
                        "getNextApiKey",
                        keys
                );

        String k3 = ReflectionTestUtils.invokeMethod(
                        chatService,
                        "getNextApiKey",
                        keys
                );

        assertEquals("KEY1", k1);
        assertEquals("KEY2", k2);
        assertEquals("KEY3", k3);
    }





}
