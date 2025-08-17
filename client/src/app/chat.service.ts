import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

export interface ChatMessage {
  content: string;
  type: 'CLIENT' | 'SUPPORT';
  status: 'SENT' | 'RECEIVED';
  userId: number;
}

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private ws?: WebSocket;
  private messages$ = new Subject<ChatMessage>();

  connect(): void {
    if (this.ws && (this.ws.readyState === WebSocket.OPEN || this.ws.readyState === WebSocket.CONNECTING)) {
      return;
    }
    this.ws = new WebSocket('ws://localhost:8080/ws/chat');
    this.ws.onmessage = (event) => {
      const msg = JSON.parse(event.data);
      this.messages$.next(msg);
    };
    this.ws.onclose = () => {
      setTimeout(() => this.connect(), 2000); // Reconnexion simple
    };
  }

  send(message: ChatMessage): void {
    if (this.ws && this.ws.readyState === WebSocket.OPEN) {
      this.ws.send(JSON.stringify(message));
    }
  }

  getMessages(): Observable<ChatMessage> {
    return this.messages$.asObservable();
  }
}
