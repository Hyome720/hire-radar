import { useCallback, useEffect, useMemo, useState } from 'react'
import {
  type Company,
  type PostingSummary,
  createCompany,
  getCompanies,
  getPostings,
} from '../services/api'

export function useHireRadar() {
  const [companies, setCompanies] = useState<Company[]>([])
  const [postings, setPostings] = useState<PostingSummary[]>([])
  const [query, setQuery] = useState('')
  const [companyId, setCompanyId] = useState<number | undefined>()
  const [openOnly, setOpenOnly] = useState(true)
  const [sortMode, setSortMode] = useState<'latest' | 'deadline'>('latest')
  const [loading, setLoading] = useState(false)
  const [message, setMessage] = useState('')

  const load = useCallback(async () => {
    setLoading(true)
    try {
      const [companyData, postingData] = await Promise.all([
        getCompanies(),
        getPostings({ query, companyId, openOnly }),
      ])
      setCompanies(companyData)
      setPostings(postingData)
      setMessage('')
    } catch {
      setMessage('백엔드 연결을 확인해 주세요.')
    } finally {
      setLoading(false)
    }
  }, [companyId, openOnly, query])

  useEffect(() => {
    void load()
  }, [load])

  const sortedPostings = useMemo(() => {
    return [...postings].sort((a, b) => {
      if (sortMode === 'deadline') {
        return (a.posting.deadline || '9999-12-31').localeCompare(b.posting.deadline || '9999-12-31')
      }
      return b.posting.createdAt.localeCompare(a.posting.createdAt)
    })
  }, [postings, sortMode])

  async function addCompany(company: Company) {
    await createCompany(company)
    await load()
  }

  return {
    companies,
    postings: sortedPostings,
    query,
    setQuery,
    companyId,
    setCompanyId,
    openOnly,
    setOpenOnly,
    sortMode,
    setSortMode,
    loading,
    message,
    addCompany,
    reload: load,
  }
}
