import {useEffect} from "react"
import CustomButton from "../../components/CustomButton.tsx";
import {useAppStore} from "../../store/appStore.ts";
import Criteria from "./Criteria.tsx";
import type {Filter} from "../../types/filter.ts";

type CriteriaRowProps = {
  rows: Filter[],
  setRows: (rows: Filter[]) => void,
  setName: (name: string) => void
}

export default function FilterManager({rows, setRows, setName}: CriteriaRowProps) {
  const defaultName: string = "My filter"
  const defaultCriteria: Filter = {criteria: "AMOUNT", operator: "GREATER_THAN", value: ""}
  const systemFilters = useAppStore((s) => s.systemFilters)
  const editFilter = useAppStore((s) => s.editFilter)

  const addRow = () => {
    setRows([...rows, defaultCriteria])
  }

  const updateRow = (index: number, updated: Filter) => {
    const newRows = [...rows]
    const oldRow = newRows[index]

    let nextRow = {...updated}

    if (updated.criteria !== oldRow.criteria) {
      const defaults = systemFilters[updated.criteria] || []
      const defaultOperator = defaults.length > 0 ? defaults[0] : ""

      nextRow = {
        ...updated,
        operator: defaultOperator,
        value: "",
      }
    }

    newRows[index] = nextRow
    setRows(newRows)
  }

  const removeRow = (index: number) => {
    setRows(rows.filter((_, i) => i !== index))
  }

  useEffect(() => {
    if (editFilter == null) {
      setName(defaultName)
      setRows([defaultCriteria])
    } else {
      setName(editFilter.name)
      setRows(editFilter.filterList)
    }
  }, [editFilter])

  return (
      <div>
        <div className="col-span-2 py-2 font-semibold">Criteria</div>
        <div className="space-y-4">
          {rows.map((row, idx) => (
              <Criteria
                  key={idx}
                  row={row}
                  systemFilters={systemFilters}
                  onChange={(updated) => updateRow(idx, updated)}
                  onRemove={() => removeRow(idx)}
              />
          ))}

          <div className="sticky bottom-0 bg-white/50 backdrop-blur pt-3">
            <CustomButton label="Add Criteria" onClick={addRow}/>
          </div>
        </div>
      </div>
  )
}
