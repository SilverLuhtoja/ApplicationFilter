import {useState} from "react"

type ToggleProps = {
  initial?: boolean
  onToggle?: (value: boolean) => void
}

export default function Toggle({initial = false, onToggle}: ToggleProps) {
  const [enabled, setEnabled] = useState(initial)

  const handleClick = () => {
    const next = !enabled
    setEnabled(next)
    onToggle?.(next)
  }

  return (
      <button
          onClick={handleClick}
          className={`relative inline-flex h-6 w-16 items-center rounded-full transition-colors ${
              enabled ? "bg-green-500" : "bg-gray-400"
          }`}
      >
        {/* Text */}
        <span
            className={`absolute left-2 text-xs font-semibold text-white transition-opacity ${
                enabled ? "opacity-100" : "opacity-0"
            }`}
        >
        On
      </span>
        <span
            className={`absolute right-2 text-xs font-semibold text-white transition-opacity ${
                enabled ? "opacity-0" : "opacity-100"
            }`}
        >
        Off
      </span>

        {/* Knob */}
        <span
            className={`inline-block h-5 w-5 transform rounded-full bg-white shadow transition-transform ${
                enabled ? "translate-x-10" : "translate-x-1"
            }`}
        />
      </button>
  )
}