import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { ChatMessage, ChatService } from './chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  messages: ChatMessage[] = [];
  newMessage = '';
  userId = 1; // Pour le POC, id fixe
  type: 'CLIENT' | 'SUPPORT' = 'CLIENT';
  private sub?: Subscription;

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.chatService.connect();
    this.sub = this.chatService.getMessages().subscribe(msg => {
      this.messages.push(msg);
    });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  send(): void {
    if (this.newMessage.trim()) {
      const msg: ChatMessage = {
        content: this.newMessage,
        type: this.type,
        status: 'SENT',
        userId: this.userId
      };
      this.chatService.send(msg);
      this.newMessage = '';
    }
  }
}
