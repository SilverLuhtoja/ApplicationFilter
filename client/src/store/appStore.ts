import {create} from "zustand"
import {persist} from "zustand/middleware"
import type {FilterCollection} from "../types/filter.ts";
import type {Message, MessageTypes, SystemFilters} from "../types/system.ts";

type State = {
  systemFilters: SystemFilters,
  clientFilters: FilterCollection[]
  editFilter: FilterCollection | null
  systemMessage: Message | null
}

type Actions = {
  setSystemFilters: (filters: SystemFilters) => void
  setClientFilters: (filters: FilterCollection[]) => void
  setEditFilter: (filters: FilterCollection | null) => void
  setSystemMessage: (type: MessageTypes, text: string) => void
}

export const useAppStore = create<State & Actions>()(
    persist(
        (set) => ({
          systemFilters: {},
          clientFilters: [],
          editFilter: null,
          systemMessage: null,

          setSystemFilters: (systemFilters) =>
              set(() => ({
                systemFilters: Object.fromEntries(
                    Object.entries(systemFilters).map(([k, v]) => [k, Array.from(new Set(v))])
                ),
              })),
          setClientFilters: (filters) => set(() => ({
            clientFilters: filters.sort((a, b) => a.id - b.id),
          })),

          setEditFilter: (filter) =>
              set(() => ({
                editFilter: filter
                    ? {
                      ...filter,
                      filterList: filter.filterList.map((r) => ({
                        ...r,
                        criteria: r.criteria.toUpperCase(),
                        operator: r.operator.toUpperCase(),
                        value: r.value,
                      })),
                    }
                    : null,
              })),

          setSystemMessage: (type: MessageTypes, text: string) => {
            set({systemMessage: {type, text}})
            setTimeout(() => set({systemMessage: null}), 3000)
          },
        }),
        {name: "app-store"}
    )
)
