package com.selcukcihan.tutor.rds.configuration.database;

public class ConnectionProperties {
    private final String url;
    private final String user;
    private final String password;

    private ConnectionProperties(Builder builder) {
        this.url = builder.url;
        this.user = builder.user;
        this.password = builder.password;
    }

    @Override
    public String toString() {
        return String.format("url=%s user=%s password=%s", url, user, password);
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String url;
        private String user;
        private String password;

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withUser(String user) {
            this.user = user;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public ConnectionProperties build() {
            return new ConnectionProperties(this);
        }
    }
}
