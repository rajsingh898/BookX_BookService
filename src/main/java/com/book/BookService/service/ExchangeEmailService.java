package com.book.BookService.service;

import com.book.BookService.dto.ExchangeEmailRequestDTO;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeEmailService {

    private final JavaMailSender mailSender;

    public void send(ExchangeEmailRequestDTO dto) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject("📚 Book Exchange Match Found · BookX");

            helper.setTo(dto.getMyEmail());

            helper.setCc(new String[]{
                    dto.getMyEmail(),
                    dto.getOtherUserEmail()
            });

            helper.setReplyTo("bookexchange.app@gmail.com");

            helper.setText(buildHtmlBody(dto), true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send connect email", e);
        }
    }

    private String buildHtmlBody(ExchangeEmailRequestDTO dto) {

        return """
                <html>
                <body style="
                  margin:0;
                  padding:0;
                  background:#f4f6f8;
                  font-family:Arial, Helvetica, sans-serif;
                ">
                
                  <div style="
                    max-width:600px;
                    margin:30px auto;
                    background:#ffffff;
                    border-radius:8px;
                    padding:24px;
                    box-shadow:0 4px 12px rgba(0,0,0,0.08);
                  ">
                
                    <div style="text-align:center; margin-bottom:20px;">
                      <h1 style="margin:0; color:#2563eb;">BookX </h1>
                      <p style="margin:4px 0 0; color:#666; font-size:13px;">
                        Connecting readers, one book at a time
                      </p>
                    </div>
                
                    <h2 style="color:#2c3e50;">🤝 You’re Connected!</h2>
                
                    <p>
                      Hi <strong>%s</strong> and <strong>%s</strong>,
                    </p>
                
                    <p>
                      Great news! You’ve both shown interest in exchanging books.
                      We’ve connected you so you can coordinate directly.
                    </p>
                
                    <div style="
                      background:#f9fafb;
                      border-radius:6px;
                      padding:16px;
                      margin:20px 0;
                    ">
                      <h3>📖 %s is offering</h3>
                      <p><strong>Title:</strong> %s</p>
                      <p><strong>Author:</strong> %s</p>
                    </div>
                
                    <div style="
                      background:#f9fafb;
                      border-radius:6px;
                      padding:16px;
                      margin:20px 0;
                    ">
                      <h3>📖 %s is offering</h3>
                      <p><strong>Title:</strong> %s</p>
                      <p><strong>Author:</strong> %s</p>
                     </div>
                
                    <div style="
                      margin:24px 0;
                      padding:16px;
                      background:#eef6ff;
                      border-left:4px solid #3b82f6;
                      border-radius:4px;
                    ">
                      <p style="margin:0;">
                        👉 <strong>Next step:</strong><br/>
                        Click <em>Reply All</em> on this email to connect directly
                        and decide how you’d like to exchange the books.
                      </p>
                    </div>
                
                    <hr style="border:none; border-top:1px solid #eee; margin:24px 0;" />
                
                    <p style="font-size:13px; color:#777;">
                      Happy reading 📚<br/>
                      <strong>BookX Team</strong>
                    </p>
                
                  </div>
                </body>
                </html>
                """.formatted(
                dto.getMyName(),
                dto.getOtherUserName(),

                dto.getMyName(),
                dto.getMyBook().getTitle(),
                dto.getMyBook().getAuthor(),

                dto.getOtherUserName(),
                dto.getTheirBook().getTitle(),
                dto.getTheirBook().getAuthor()
        );
    }
}
