type Props = {
  onClose: () => void
}

export default function FilterHeader({onClose}: Props){
    return (
        <div className="flex items-center justify-between rounded-t-xl px-5 py-3 bg-violet-700 text-white">
            <h2 className="text-lg font-semibold">Filter</h2>
            <button onClick={onClose} className="h-8 w-8 rounded hover:bg-white/20">✕</button>
        </div>
    ) 
}