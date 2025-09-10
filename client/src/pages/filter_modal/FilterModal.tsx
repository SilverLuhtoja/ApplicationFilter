import {useEffect, useState} from "react"
import FilterHeader from "./FilterHeader.tsx"
import FilterFooter from "./FilterFooter.tsx"
import {useAppStore} from "../../store/appStore.ts";
import FilterManager from "./FilterManager.tsx";
import filterApi from "../../api/filterApi.ts";
import FilterError from "./FilterError.tsx";
import type {Filter} from "../../types/filter.ts";

type FilterModalProps = {
  open: boolean
  modalMode: boolean
  onClose: () => void
}

export default function FilterModal({open, onClose, modalMode}: FilterModalProps) {
  const [name, setName] = useState("")
  const [rows, setRows] = useState<Filter[]>([])
  const setSystemMessage = useAppStore((s) => s.setSystemMessage)
  const setClientFilters = useAppStore((s) => s.setClientFilters)
  const editFilter = useAppStore((s) => s.editFilter)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    if (open) {
      setError(null)
    }
  }, [open])

  if (!open) return null

  const handleSave = () => {
    const payload = {
      id: editFilter ? editFilter.id : null,
      name: name,
      filterList: rows
    }

    filterApi.saveFilter(payload)
    .then(() => {
      setSystemMessage("info", "Filter has been saved")
      refreshClientFilters()
      onClose()
    })
    .catch((err) => {
      setError(err.message)
    })
  }

  const refreshClientFilters = () => {
    filterApi.getFilters()
    .then((res) => setClientFilters(res))
    .catch((err) => setSystemMessage("error", err.message))
  }

  return (
      <div className={`${modalMode ? "fixed inset-5 z-50" : ""} flex items-start justify-center`}>
        <div
            className="w-[900px] max-w-[900px] min-h-[40vh] h-[40vh] max-h-[70vh] resize-y overflow-hidden rounded-xl bg-white shadow-xl flex flex-col">
          <FilterHeader onClose={onClose}/>
          {error && (<FilterError error={error}/>)}

          <div className="flex-1 min-h-0 px-6 py-6 flex flex-col gap-6 ">
            <div className="grid grid-cols-12 items-center gap-4">
              <label className="col-span-3 font-semibold">Filter name</label>
              <input
                  className="col-span-9 rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-violet-500"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
              />
            </div>

            <div className="flex-1 min-h-0 overflow-y-auto pr-2">
              <FilterManager rows={rows} setRows={setRows} setName={setName}/>
            </div>
          </div>

          <FilterFooter name={name} onClose={onClose} onSave={handleSave}/>
        </div>
      </div>
  )
}