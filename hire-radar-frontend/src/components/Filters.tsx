import type { Company } from '../services/api'

type Props = {
  companies: Company[]
  query: string
  companyId?: number
  openOnly: boolean
  sortMode: 'latest' | 'deadline'
  onQueryChange: (value: string) => void
  onCompanyChange: (value?: number) => void
  onOpenOnlyChange: (value: boolean) => void
  onSortModeChange: (value: 'latest' | 'deadline') => void
}

export function Filters({
  companies,
  query,
  companyId,
  openOnly,
  sortMode,
  onQueryChange,
  onCompanyChange,
  onOpenOnlyChange,
  onSortModeChange,
}: Props) {
  return (
    <div className="toolbar">
      <label className="search">
        <span>검색</span>
        <input value={query} onChange={(event) => onQueryChange(event.target.value)} placeholder="공고명, 기술, 설명" />
      </label>

      <label>
        <span>회사</span>
        <select
          value={companyId ?? ''}
          onChange={(event) => onCompanyChange(event.target.value ? Number(event.target.value) : undefined)}
        >
          <option value="">전체</option>
          {companies.map((company) => (
            <option key={company.id} value={company.id}>
              {company.name}
            </option>
          ))}
        </select>
      </label>

      <label className="toggle">
        <input type="checkbox" checked={openOnly} onChange={(event) => onOpenOnlyChange(event.target.checked)} />
        <span>모집중</span>
      </label>

      <div className="segments" aria-label="정렬">
        <button className={sortMode === 'latest' ? 'active' : ''} onClick={() => onSortModeChange('latest')}>
          최신순
        </button>
        <button className={sortMode === 'deadline' ? 'active' : ''} onClick={() => onSortModeChange('deadline')}>
          마감임박
        </button>
      </div>
    </div>
  )
}
