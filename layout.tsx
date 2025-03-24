import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
    title: "Auracle",
    description: "X clone using Next.js, SpringBoot, and  PostgreSQL."
}

export default function RootLayout({
    children,
}: {
    children: React.ReactNode
}) {
    return (
        <html lang="en">
            <body>{children}</body>
        </html>
    )
}