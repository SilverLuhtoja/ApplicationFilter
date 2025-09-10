import type {Message} from "../types/system.ts";

type SystemMessageProps = {
  systemMessage: Message
}

export default function SystemMessage({systemMessage}: SystemMessageProps) {
  return systemMessage.type === "error" ? (
      <div className="fixed top-6 right-6 z-50 rounded-lg bg-red-600 px-6 py-3 shadow-lg">
        <p className="text-white font-semibold">{systemMessage.text}</p>
      </div>
  ) : (
      <div className="fixed top-6 right-6 z-50 rounded-lg bg-green-600 px-6 py-3 shadow-lg">
        <p className="text-white font-semibold">{systemMessage.text}</p>
      </div>
  )
}

