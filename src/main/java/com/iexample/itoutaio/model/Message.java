package com.iexample.itoutaio.model;

import java.util.Date;

/*CREATE TABLE `message` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `from_id` INT NULL,
        `to_id` INT NULL,
        `content` TEXT NULL,
        `created_date` DATETIME NULL,
        `has_read` INT NULL,
        `conversation_id` VARCHAR(45) NOT NULL,
        PRIMARY KEY (`id`),
        INDEX `conversation_index` (`conversation_id` ASC),
        INDEX `created_date` (`created_date` ASC))*/
public class Message {
    int id;
    int fromId;
    int toId;
    String content;
    Date createdDate;
    int hasRead;
    String conversationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    //public String getConversationId() {
    //    return conversationId;
    //}
    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        }
        return String.format("%d_%d", toId, fromId);
    }

    //public void setConversationId(String conversationId) {
    //    this.conversationId = conversationId;
    //}
}
