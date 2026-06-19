import axios from 'axios'

export type Company = {
  id?: number
  name: string
  careerUrl: string
  scrapingType: string
  checkIntervalMinutes: number
  isActive: number
}

export type Posting = {
  id: number
  companyId: number
  title: string
  status: string
  postedDate: string
  deadline: string
  url: string
  description: string
  createdAt: string
}

export type PostingSummary = {
  posting: Posting
  fitScore: number
  fitReasons: string[]
}

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL ?? '/api',
})

export async function getCompanies() {
  const { data } = await api.get<Company[]>('/companies')
  return data
}

export async function createCompany(company: Company) {
  const { data } = await api.post<Company>('/companies', company)
  return data
}

export async function getPostings(params: {
  query?: string
  companyId?: number
  openOnly?: boolean
}) {
  const { data } = await api.get<PostingSummary[]>('/postings', { params })
  return data
}