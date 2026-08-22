package com.example.chatbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;

    private List<Message> messageList;
    private String currentDeviceId;

    public MessageAdapter(List<Message> messageList, String currentDeviceId) {
        this.messageList = messageList;
        this.currentDeviceId = currentDeviceId;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (Objects.equals(message.getSenderId(), currentDeviceId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        if (holder instanceof SentMessageViewHolder) {
            ((SentMessageViewHolder) holder).textContent.setText(message.getText());
        } else {
            ((ReceivedMessageViewHolder) holder).textSender.setText(message.getSenderName());
            ((ReceivedMessageViewHolder) holder).textContent.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textContent;

        public SentMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_content);
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        TextView textSender, textContent;

        public ReceivedMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textSender = itemView.findViewById(R.id.text_sender);
            textContent = itemView.findViewById(R.id.text_content);
        }
    }
}