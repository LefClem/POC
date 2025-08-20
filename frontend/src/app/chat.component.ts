import { Component, OnInit, OnDestroy } from "@angular/core";
import { Subscription } from "rxjs";
import { ChatMessage, ChatService } from "./chat.service";

@Component({
	selector: "app-chat",
	templateUrl: "./chat.component.html",
	styleUrls: ["./chat.component.css"],
})
export class ChatComponent implements OnInit, OnDestroy {
	messages: ChatMessage[] = [];
	newMessage = "";
	type: "CLIENT" | "SUPPORT" = "CLIENT";
	private sub?: Subscription;

	constructor(private chatService: ChatService) {}

	get userId(): number {
		return this.type === "CLIENT" ? 1 : 2;
	}

	ngOnInit(): void {
		this.chatService.connect();
		this.sub = this.chatService.getMessages().subscribe((msg) => {
			console.log(msg);
			this.messages.push(msg);
		});
	}

	ngOnDestroy(): void {
		this.sub?.unsubscribe();
	}

	send(): void {
		if (this.newMessage.trim()) {
			const sendingDate = new Date().toISOString();
			const msg: ChatMessage = {
				content: this.newMessage,
				type: this.type,
				status: "SENT",
				userId: this.userId,
				sendingDate: sendingDate,
			};
			this.chatService.send(msg);
			this.newMessage = "";
		}
	}
}
