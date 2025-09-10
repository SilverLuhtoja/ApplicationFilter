export type SystemFilters = Record<string, string[]>;

export type MessageTypes = "error" | "info"
export type Message = {
  type: MessageTypes
  text: string
}