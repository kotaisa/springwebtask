package com.example.springwebtask.Entity;

public record usersRecord(int id, String login_id, String password, String name, int role) {

}

//login_idの昇順でレコードを並べればいいから、レコードにidを入れる必要はなかったりして？