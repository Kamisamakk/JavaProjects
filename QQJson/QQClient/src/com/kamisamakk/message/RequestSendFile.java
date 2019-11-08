package com.kamisamakk.message;

public class RequestSendFile {
    private String sendId;
    private String recvId;
    private String type;
    private String fileName;
    private long fileSize;
    private String ip;
    private int port;

    public RequestSendFile(){
        this.type=JsonMessage.FILE;
    }

    public RequestSendFile(String sendId, String recvId, String fileName, long fileSize, String ip, int port) {
        this.type=JsonMessage.FILE;
        this.sendId = sendId;
        this.recvId = recvId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.ip = ip;
        this.port = port;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getRecvId() {
        return recvId;
    }

    public void setRecvId(String recvId) {
        this.recvId = recvId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
