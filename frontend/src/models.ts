export interface UserBasic {
    id: number;
    username: string;
    displayName: string;
    profileImg?: string;
    bio?: string;
    email: string;
}

export interface PostBasic {
    id: number;
    message: string;
    dateTime: string;
    image?: string;
}

export interface User extends UserBasic {
    posts?: Post[];
    followers?: User[];
    following?: User[];
    likedPosts?: Post[];
}

export interface Post extends PostBasic {
    user: UserBasic;
    likes: UserBasic[];
}