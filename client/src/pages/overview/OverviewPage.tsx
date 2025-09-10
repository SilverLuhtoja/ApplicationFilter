import {useEffect} from "react";
import {useAppStore} from "../../store/appStore.ts";
import filterApi from "../../api/filterApi.ts";

export default function OverviewPage({onEdit}: { onEdit: () => void }) {
  const setClientFilters = useAppStore((s) => s.setClientFilters)
  const clientFilters = useAppStore((s) => s.clientFilters)
  const setEditFilter = useAppStore((s) => s.setEditFilter)
  const setSystemMessage = useAppStore((s) => s.setSystemMessage)

  const getClientFilters = () => {
    filterApi.getFilters()
    .then((res) => {
      setClientFilters(res)
    })
    .catch(() => {
      setSystemMessage("error", "Server is not available")
    })
  }

  const onRemove = (id: number) => {
    filterApi.deleteFilter(id)
    .then((res) => {
      setSystemMessage("info", res)
      getClientFilters()
    })
    .catch((err) => setSystemMessage("error", err.message))
  }

  const onUpdate = (name: string) => {
    setEditFilter(clientFilters.find((f) => f.name === name) ?? null)
    onEdit()
  }

  useEffect(() => {
    getClientFilters()
  }, [])

  return (
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-4 text-gray-700">Saved Filters</h1>

        {clientFilters.length === 0 ? (
            <p className="text-xl text-gray-500">No saved filters</p>
        ) : (
            <div className="overflow-x-auto rounded-2xl shadow">
              <table className="min-w-full border border-gray-200 text-sm">
                <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
                <tr>
                  <th className="px-4 py-2">Filter Name</th>
                  <th className="px-4 py-2">Criteria</th>
                  <th className="px-4 py-2">Operator</th>
                  <th className="px-4 py-2">Value</th>
                  <th className="px-4 py-2">Command</th>
                </tr>
                </thead>
                <tbody>
                {clientFilters.map((filter) =>
                    filter.filterList.map((f, i) => (
                        <tr
                            key={`${filter.name}-${i}`}
                            className="odd:bg-white even:bg-gray-50 hover:bg-gray-100"
                        >
                          {i === 0 ? (
                              <td
                                  rowSpan={filter.filterList.length}
                                  className="px-4 py-2 font-medium text-gray-800 align-top border"
                              >
                                {filter.name}
                              </td>
                          ) : null}

                          <td className="px-4 py-2 border">{f.criteria}</td>
                          <td className="px-4 py-2 border">{f.operator}</td>
                          <td className="px-4 py-2 border">{f.value}</td>

                          {i === 0 ? (
                              <td
                                  rowSpan={filter.filterList.length}
                                  className=" px-4 py-2 border text-center align-top"
                              >
                                <button
                                    onClick={() => onRemove(filter.id)}
                                    className="btn red-button"
                                >
                                  Remove
                                </button>

                                <button
                                    onClick={() => onUpdate(filter.name)}
                                    className="btn green-button"
                                >
                                  Edit
                                </button>
                              </td>
                          ) : null}
                        </tr>
                    ))
                )}
                </tbody>
              </table>
            </div>
        )}
      </div>
  );
}