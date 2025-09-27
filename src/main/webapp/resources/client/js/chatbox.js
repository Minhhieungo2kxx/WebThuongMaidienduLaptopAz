// Biến global
let isWaitingForResponse = false;
const chatBody = document.getElementById("chat-box-body");
const chatInput = document.getElementById("chat-input");
const chatSend = document.getElementById("chat-send");

// Toggle chat box
function toggleChatBox() {
    const chatBox = document.getElementById("chat-box");
    const isVisible = chatBox.style.display === "flex";

    chatBox.style.display = isVisible ? "none" : "flex";

    if (!isVisible) {
        chatInput.focus();
        loadChatHistory();
    }
}

// Gửi tin nhắn
async function sendMessage() {
    const message = chatInput.value.trim();

    if (message === "" || isWaitingForResponse) {
        return;
    }

    displayUserMessage(message);
    chatInput.value = "";
    setLoadingState(true);
    showTypingIndicator();

    try {
        const response = await fetch('/api/chat/send', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ message }),
            credentials: 'same-origin'
        });

        const data = await response.json();

        hideTypingIndicator();

        if (data.success && data.data && data.data.response) {
            displayAIMessage(data.data.response);
        } else {
            displayAIMessage("Xin lỗi, đã có lỗi xảy ra. Vui lòng thử lại sau.");
            console.error('API Error:', data);
        }

    } catch (error) {
        hideTypingIndicator();
        displayAIMessage("Không thể kết nối đến server. Vui lòng thử lại sau.");
        console.error('Network Error:', error);
    } finally {
        setLoadingState(false);
    }
}

// Hiển thị tin nhắn của user
function displayUserMessage(message) {
    const messageDiv = document.createElement("div");
    messageDiv.className = "message user-message";
    messageDiv.innerHTML = `
        <div>${escapeHtml(message)}</div>
        <div class="timestamp">${getCurrentTime()}</div>
    `;
    chatBody.appendChild(messageDiv);
    scrollToBottom();
}

// Hiển thị tin nhắn từ AI (có định dạng)
function displayAIMessage(message) {
    const messageDiv = document.createElement("div");
    messageDiv.className = "message ai-message";
    messageDiv.innerHTML = `
        <div>${formatAIMessage(message)}</div>
        <div class="timestamp">${getCurrentTime()}</div>
    `;
    chatBody.appendChild(messageDiv);
    scrollToBottom();
}

// Định dạng nội dung AI trả về
function formatAIMessage(rawMessage) {
    if (!rawMessage) return '';

    const escaped = escapeHtml(rawMessage);
    const withLineBreaks = escaped.replace(/\n/g, "<br>");
    const formatted = withLineBreaks.replace(/- (.+?)<br>/g, "<li>$1</li>");

    return formatted.includes("<li>")
        ? `<ul>${formatted}</ul>`
        : withLineBreaks;
}

// Escape HTML để tránh XSS
function escapeHtml(text) {
    return text.replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

// Lấy giờ hiện tại (hh:mm)
function getCurrentTime() {
    const now = new Date();
    return now.toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' });
}

// Hiển thị typing indicator
function showTypingIndicator() {
    const existingIndicator = document.querySelector('.typing-indicator');
    if (existingIndicator) return;

    const typingDiv = document.createElement("div");
    typingDiv.className = "typing-indicator";
    typingDiv.innerHTML = 'AI đang trả lời<span class="typing-dots"></span>';
    chatBody.appendChild(typingDiv);
    scrollToBottom();
}

// Ẩn typing indicator
function hideTypingIndicator() {
    const typingIndicator = document.querySelector('.typing-indicator');
    if (typingIndicator) {
        typingIndicator.remove();
    }
}

// Set loading state
function setLoadingState(loading) {
    isWaitingForResponse = loading;
    chatSend.disabled = loading;
    chatInput.disabled = loading;
    chatSend.innerHTML = loading ? '<span class="spinner"></span>Đang gửi...' : 'Gửi';
}

// Scroll to bottom
function scrollToBottom() {
    chatBody.scrollTop = chatBody.scrollHeight;
}

// Load chat history
async function loadChatHistory() {
    try {
        const response = await fetch('/api/chat/history', {
            method: 'GET',
            credentials: 'same-origin'
        });

        const data = await response.json();

        if (data.success && data.data && data.data.length > 0) {
            const systemMessage = chatBody.querySelector('.system-message');
            chatBody.innerHTML = '';
            if (systemMessage) chatBody.appendChild(systemMessage);

            data.data.forEach(item => {
                if (item.message) displayUserMessage(item.message);
                if (item.response) displayAIMessage(item.response);
            });
        }
    } catch (error) {
        console.error('Error loading chat history:', error);
    }
}

// Clear chat history
async function clearChatHistory() {
    if (!confirm('Bạn có chắc chắn muốn xóa toàn bộ lịch sử chat?')) return;

    try {
        const response = await fetch('/api/chat/history/clear', {
            method: 'DELETE',
            credentials: 'same-origin'
        });

        const data = await response.json();

        if (data.success) {
            chatBody.innerHTML = '';
            const systemMessage = document.createElement("div");
            systemMessage.className = "system-message";
            systemMessage.textContent = "Lịch sử chat đã được xóa. Bạn có thể bắt đầu cuộc trò chuyện mới!";
            chatBody.appendChild(systemMessage);
        } else {
            alert('Không thể xóa lịch sử chat. Vui lòng thử lại.');
        }
    } catch (error) {
        console.error('Error clearing chat history:', error);
        alert('Đã có lỗi xảy ra khi xóa lịch sử chat.');
    }
}

// Handle Enter key
function handleKey(event) {
    if (event.key === "Enter" && !event.shiftKey) {
        event.preventDefault();
        sendMessage();
    }
}
document.addEventListener('DOMContentLoaded', function () {
    const chatBox = document.getElementById("chat-box");
    chatBox.style.display = 'flex'; // Mở khung chat ngay
    loadChatHistory(); // Load lịch sử ngay

    setTimeout(() => chatInput.focus(), 100);

    const observer = new MutationObserver(function (mutations) {
        mutations.forEach(function (mutation) {
            if (mutation.type === 'attributes' && mutation.attributeName === 'style') {
                if (chatBox.style.display === 'flex') {
                    setTimeout(() => chatInput.focus(), 100);
                    loadChatHistory();
                }
            }
        });
    });

    observer.observe(chatBox, {
        attributes: true,
        attributeFilter: ['style']
    });
});


// Handle connection errors
window.addEventListener('online', () => console.log('Connection restored'));
window.addEventListener('offline', () => {
    console.log('Connection lost');
    displayAIMessage("Mất kết nối internet. Vui lòng kiểm tra kết nối và thử lại.");
});



