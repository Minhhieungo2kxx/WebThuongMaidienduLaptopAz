<%@ page contentType="text/html;charset=UTF-8" language="java" %>


      <!-- Biểu tượng tin nhắn -->
      <div id="chat-icon" onclick="toggleChatBox()">
            💬
      </div>

      <!-- Khung chat -->
      <div id="chat-box">
            <div id="chat-box-header">
                  <span>🤖 AI Support</span>
                  <div>
                        <button id="clear-history-btn" onclick="clearChatHistory()" title="Xóa lịch sử">🗑️</button>
                        <button id="chat-close-btn" onclick="toggleChatBox()">×</button>
                  </div>
            </div>
            <div id="chat-box-body">
                  <div class="system-message">Xin chào! Tôi là trợ lý AI. Bạn cần hỗ trợ gì?</div>
            </div>
            <div id="chat-box-footer">
                  <input type="text" id="chat-input" placeholder="Nhập tin nhắn..." onkeydown="handleKey(event)" />
                  <button id="chat-send" onclick="sendMessage()">Gửi</button>
            </div>
      </div>