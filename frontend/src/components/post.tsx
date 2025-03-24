"use client";

import type { Post } from "@/models";

interface PostProps {
    post: Post
}

export default function Post({ post }: PostProps) {
    const displayName = "zekken";
    const username = "zekken";
    const message = "hello world!";
    const dateTime = "now";
    const replies = [
        {
            displayName: "TenZ",
            username: "TenZ",
            message: "wassup!",
            dateTime: "now"
        },
        {
            displayName: "JohnQT",
            username: "johnqt",
            message: "yooooo!",
            dateTime: "now"
        }
    ]
    const likes = [
        "TenZ", "johnqt"
    ]

    return (
        <div>
            <div className="flex">
                <p>{displayName}</p>
                <p>{username}</p>
                <p>{dateTime}</p>
            </div>
            <p>{message}</p>
            <div className="flex">
                <p>Likes: {likes.length}</p>
                <p>Replies: {replies.length}</p>
            </div>
        </div>
    )
}