type Props = {
  label: string
  onClick?: () => void
}

export default function CustomButton({label, onClick}: Props) {
  return (
      <button
          onClick={onClick}
          className="large-btn violet-button"
      >
        {label}
      </button>
  )
}