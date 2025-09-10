import type {Filter} from "../../types/filter.ts";

type CriteriaRowProps = {
  row: Filter
  systemFilters: Record<string, string[]>
  onChange: (row: Filter) => void
  onRemove: () => void
}

const CriteriaToInputType: Record<string, string> = {
  AMOUNT: "number",
  TITLE: "text",
  DATE: "date"
}

export default function Criteria({row, systemFilters, onChange, onRemove}: CriteriaRowProps) {
  const {criteria, operator, value} = row

  return (
      <div className="grid grid-cols-12 gap-4 items-center">
        {/* Criteria */}
        <select
            className="col-span-4 w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-violet-500"
            value={criteria}
            onChange={(e) => onChange({...row, criteria: e.target.value, operator: ""})}
        >
          {Object.keys(systemFilters).map((crit) => (
              <option key={crit} value={crit}>
                {crit}
              </option>
          ))}
        </select>

        {/* Operator */}
        <select
            className="col-span-4 w-full rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-violet-500"
            value={operator}
            onChange={(e) => onChange({...row, operator: e.target.value})}
        >
          {systemFilters[criteria]?.map((op) => (
              <option
                  className="bg-white text-black checked:bg-blue-500 checked:text-white"
                  key={op}
                  value={op}>
                {op}
              </option>
          ))}
        </select>

        {/* Value */}
        <input
            className="col-span-3 rounded border border-gray-300 px-3 py-2 focus:outline-none focus:ring-2 focus:ring-violet-500"
            type={CriteriaToInputType[criteria]}
            placeholder={CriteriaToInputType[criteria]}
            value={value}
            onChange={(e) => onChange({...row, value: e.target.value})}
        />

        {/* Remove button */}
        <button
            type="button"
            className="col-span-1 px-2 py-1 rounded bg-pink-500  text-white hover:bg-pink-600"
            onClick={onRemove}
        >
          ×
        </button>
      </div>
  )
}
