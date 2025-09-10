type Props = {
  name: string
  onClose: () => void
  onSave: (data: { name: string }) => void
}

export default function FilterFooter({ name, onClose, onSave }: Props) {
  return (
    <div className="flex justify-end gap-3 rounded-b-xl bg-gray-100 px-6 py-4">
      <button
        className="rounded bg-gray-500 px-5 py-2 text-white"
        onClick={onClose}
      >
        CLOSE
      </button>
      <button
        className="rounded px-5 py-2 font-semibold violet-button"
        onClick={() => onSave({ name })}
      >
        SAVE
      </button>
    </div>
  );
}
