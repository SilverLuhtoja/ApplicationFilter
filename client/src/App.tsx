import {useEffect, useState} from "react"
import CustomButton from "./components/CustomButton.tsx"
import OverviewPage from "./pages/overview/OverviewPage.tsx"
import FilterModal from "./pages/filter_modal/FilterModal.tsx"
import {useAppStore} from "./store/appStore.ts";
import filterApi from "./api/filterApi.ts";
import SystemMessage from "./components/SystemMessage.tsx";
import Toggle from "./components/Toggle.tsx";

export default function App() {
  const setSystemFilters = useAppStore((s) => s.setSystemFilters)
  const setEditFilter = useAppStore((s) => s.setEditFilter)
  const setSystemMessage = useAppStore((s) => s.setSystemMessage)
  const systemMessage = useAppStore((s) => s.systemMessage)
  const [open, setOpen] = useState(false)
  const [modalMode, setModalMode] = useState(false)

  useEffect(() => {
    filterApi.getSystemFilters()
    .then((data) => {
      setSystemFilters(data.systemFilters)
    })
    .catch((err) => {
      setSystemMessage("error", err.message)
    })
  }, [])

  return (
      <div className="flex-col items-start justify-center">
        <div className="w-full flex items-center gap-2 px-12 py-4 text-xl bg-violet-800 text-white relative">
          <img
              src="../public/assets/askend.png"
              alt="logo"
              className="h-6 object-cover"
          />

          <h1> | FilterApplication </h1>

          <div className="text-sm absolute right-6 flex items-center gap-2">
            Modal mode <Toggle onToggle={() => setModalMode(!modalMode)}/>
          </div>
        </div>

        {systemMessage && (<SystemMessage systemMessage={systemMessage}/>)}

        <OverviewPage onEdit={() => setOpen(true)}/>


        <FilterModal
            open={open}
            modalMode={modalMode}
            onClose={() => setOpen(false)}
        />

        {!open && <CustomButton label="Add filter" onClick={() => {
          setOpen(!open)
          setEditFilter(null)
        }}/>
        }
      </div>
  )
}
