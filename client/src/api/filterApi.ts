type SaveFilterPayload = {
  name: string
  filterList: {
    criteria: string
    operator: string
    value: string
  }[]
}

export class FilterApi {
  private baseUrl: string

  constructor(baseUrl: string = "http://localhost:8000/api") {
    this.baseUrl = baseUrl
  }

  async saveFilter(payload: SaveFilterPayload) {
    const res = await fetch(this.baseUrl, {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(payload),
    })

    if (!res.ok) {
      const data = await res.json()
      throw new Error(data.message || `Failed to save filter: ${res.status}`)
    }

    return res.json()
  }

  async deleteFilter(id: number) {
    const res = await fetch(`${this.baseUrl}/delete/${id}`, {
      method: "DELETE",
      headers: {"Content-Type": "application/json"},
    })

    if (!res.ok) {
      const data = await res.json()
      throw new Error(data.message || `Failed to delete filter: ${res.status}`)
    }

    return "Filter has been deleted"
  }

  async getFilters() {
    const res = await fetch(this.baseUrl, {
      method: "GET",
      headers: {"Content-Type": "application/json"},
    })

    if (!res.ok) {
      const data = await res.json()
      throw new Error(data.message || `Failed to fetch filters: ${res.status}`)
    }

    return res.json()
  }

  async getSystemFilters() {
    const res = await fetch(`${this.baseUrl}/filters`, {
      method: "GET",
      headers: {"Content-Type": "application/json"},
    })

    if (!res.ok) {
      const data = await res.json()
      throw new Error(data.message || `Failed to fetch system filters: ${res.status}`)
    }

    return res.json()
  }
}

export default new FilterApi();