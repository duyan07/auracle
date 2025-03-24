import Link from "next/link";

export default function Landing() {
    return <div className="flex justify-evenly">
        <h1>Welcome to <br /><span className="text-purple-500">Auracle</span></h1>
        <section className="flex flex-col gap-2">
            <form className="flex flex-col gap-3 items-center w-full max-w-md mx-auto">
                <div className="w-full">
                    <p>Email or username</p>
                    <input className="border rounded border-gray-400 w-full" placeholder=" m@example.com"></input>
                </div>
                <div className="w-full">
                    <p>Password</p>
                    <input className="border rounded border-gray-400 w-full" placeholder=""></input>
                </div>
                <button className="rounded bg-purple-500 py-1 px-4 w-full">Sign in</button>
            </form>
            <hr></hr>
            <p>Don't have an account? <Link href="/sign-up" className="text-purple-500 hover:underline">Sign up</Link></p>
            <p>Or, <Link href="/home" className="text-purple-500 hover:underline">just browse</Link></p>
        </section>
    </div>
}