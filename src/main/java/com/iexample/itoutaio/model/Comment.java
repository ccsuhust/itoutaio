package com.iexample.itoutaio.model;

import org.joda.time.DateTime;


import java.util.Date;

/*DROP TABLE IF EXISTS `comment`;
        CREATE TABLE `comment` (
        `id` INT NOT NULL AUTO_INCREMENT,
        `content` TEXT NOT NULL,
        `user_id` INT NOT NULL,
        `entity_id` INT NOT NULL,
        `entity_type` INT NOT NULL,
        `created_date` DATETIME NOT NULL,
        `status` INT NOT NULL DEFAULT 0,
        PRIMARY KEY (`id`),
        INDEX `entity_index` (`entity_id` ASC, `entity_type` ASC)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;*/
public class Comment {
    private int id;
    private String content;
    private int userId;
    private int entityId;
    private int entityType;
    private Date createdDate;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", entityId=" + entityId +
                ", entityType=" + entityType +
                ", createdDate=" + createdDate +
                ", status=" + status +
                '}';
    }
}
