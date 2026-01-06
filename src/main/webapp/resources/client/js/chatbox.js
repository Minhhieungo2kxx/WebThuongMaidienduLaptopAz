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

async function sendMessage() {
    const message = chatInput.value.trim();

    if (message === "" || isWaitingForResponse) {
        return;
    }

    // Hiển thị message của user ngay khi gửi
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

        if (data.success && data.data) {
            // Chỉ hiển thị AI message
            displayAIMessage(data.data);
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
    let text = '';
    let timestamp = null;

    if (!message) {
        text = "AI chưa trả lời";
    } else if (typeof message === 'string') {
        text = message;
    } else if (typeof message === 'object') {
        text = message.message;
        timestamp = message.timestamp || null;
    }
    const messageDiv = document.createElement("div");
    messageDiv.className = "message user-message";
    messageDiv.innerHTML = `
        <div>${escapeHtml(text)}</div>
        <div class="timestamp">${formatTimestamp(timestamp)}</div>
    `;
    chatBody.appendChild(messageDiv);
    scrollToBottom();
}
function displayAIMessage(message) {
    let text = '';
    let timestamp = null;

    if (!message) {
        text = "AI chưa trả lời";
    } else if (typeof message === 'string') {
        text = message;
    } else if (typeof message === 'object') {
        text = message.response || "AI chưa trả lời";
        timestamp = message.timestamp || null;
    }

    const messageDiv = document.createElement("div");
    messageDiv.className = "message ai-message";
    messageDiv.innerHTML = `
        <div>${formatAIMessage(text)}</div>
        <div class="timestamp">${formatTimestamp(timestamp)}</div>
    `;
    chatBody.appendChild(messageDiv);
    scrollToBottom();
}

// Hiển thị tin nhắn từ AI (hỗ trợ object hoặc string)

function formatAIMessage(rawMessage) {
    if (!rawMessage) return '';

    // Escape HTML để tránh XSS
    let escaped = escapeHtml(rawMessage);

    // In đậm: *text* → <b>text</b>
    escaped = escaped.replace(/\*(.*?)\*/g, '<b>$1</b>');

    // In nghiêng: _text_ → <i>text</i>
    escaped = escaped.replace(/_(.*?)_/g, '<i>$1</i>');

    // Code inline: `code` → <code>code</code>
    escaped = escaped.replace(/`(.*?)`/g, '<code>$1</code>');

    // Tách dòng
    const lines = escaped.split('\n');

    let result = '';
    let inList = false;

    for (let line of lines) {
        line = line.trim();

        if (line.startsWith('- ')) {
            // Mở <ul> nếu chưa mở
            if (!inList) {
                result += '<ul>';
                inList = true;
            }
            result += `<li>${line.slice(2)}</li>`;
        } else {
            // Đóng <ul> nếu đang mở
            if (inList) {
                result += '</ul>';
                inList = false;
            }
            // Dòng bình thường: nếu rỗng thì <br>, không thì <div>
            result += line ? `<div>${line}</div>` : '<br>';
        }
    }

    // Nếu còn list mở cuối cùng thì đóng
    if (inList) result += '</ul>';

    return result;
}


// Escape HTML để tránh XSS
function escapeHtml(text) {
    return text.replace(/</g, "&lt;").replace(/>/g, "&gt;");
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
                if (item.message) displayUserMessage(item);
                if (item.response || item.timestamp) displayAIMessage(item); // truyền object nguyên vẹn
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
function parseTimestamp(ts) {
    if (!ts) return new Date();

    if (ts instanceof Date) return ts;

    if (typeof ts === 'number') {
        const d = new Date(ts);
        return isNaN(d) ? new Date() : d;
    }

    if (typeof ts === 'string') {
        // Bỏ microseconds nếu có
        let clean = ts.split('.')[0]; // "2026-01-06 17:42:49"
        // Tách từng phần
        const match = clean.match(/^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/);
        if (match) {
            const [_, year, month, day, hour, minute, second] = match;
            return new Date(
                Number(year),
                Number(month) - 1, // JS months 0-11
                Number(day),
                Number(hour),
                Number(minute),
                Number(second)
            );
        }

        // fallback nếu không khớp
        const d = new Date(clean.replace(' ', 'T'));
        return isNaN(d) ? new Date() : d;
    }

    // fallback mọi trường hợp khác
    return new Date();
}

function formatTimestamp(ts) {
    const now = parseTimestamp(ts);

    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');

    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    return `${day}-${month}-${year} ${hours}:${minutes}:${seconds}`;
}



// DOM ready
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
