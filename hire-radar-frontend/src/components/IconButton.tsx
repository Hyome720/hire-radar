import type { ButtonHTMLAttributes, ReactNode } from 'react'

type Props = ButtonHTMLAttributes<HTMLButtonElement> & {
  label: string
  icon: ReactNode
}

export function IconButton({ label, icon, ...props }: Props) {
  return (
    <button {...props} title={label} aria-label={label}>
      <span aria-hidden="true">{icon}</span>
      <span>{label}</span>
    </button>
  )
}
