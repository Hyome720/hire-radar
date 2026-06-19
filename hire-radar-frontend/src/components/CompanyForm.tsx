import { useState, type FormEvent } from 'react'
import type { Company } from '../services/api'

type Props = {
  onSubmit: (company: Company) => Promise<void>
}

export function CompanyForm({ onSubmit }: Props) {
  const [name, setName] = useState('')
  const [careerUrl, setCareerUrl] = useState('')
  const [scrapingType, setScrapingType] = useState('STATIC')
  const [checkIntervalMinutes, setCheckIntervalMinutes] = useState(60)
  const [isActive, setIsActive] = useState(true)

  async function submit(event: FormEvent) {
    event.preventDefault()
    await onSubmit({
      name,
      careerUrl,
      scrapingType,
      checkIntervalMinutes,
      isActive: isActive ? 1 : 0,
    })
    setName('')
    setCareerUrl('')
  }

  return (
    <form className="company-form" onSubmit={submit}>
      <h2>기업 추가</h2>
      <label>
        <span>기업명</span>
        <input value={name} onChange={(event) => setName(event.target.value)} required />
      </label>
      <label>
        <span>채용 URL</span>
        <input type="url" value={careerUrl} onChange={(event) => setCareerUrl(event.target.value)} required />
      </label>
      <label>
        <span>수집 방식</span>
        <select value={scrapingType} onChange={(event) => setScrapingType(event.target.value)}>
          <option value="STATIC">STATIC</option>
          <option value="DYNAMIC">DYNAMIC</option>
        </select>
      </label>
      <label>
        <span>체크 주기</span>
        <input
          type="number"
          min="5"
          value={checkIntervalMinutes}
          onChange={(event) => setCheckIntervalMinutes(Number(event.target.value))}
        />
      </label>
      <label className="toggle">
        <input type="checkbox" checked={isActive} onChange={(event) => setIsActive(event.target.checked)} />
        <span>활성</span>
      </label>
      <button type="submit">추가</button>
    </form>
  )
}
