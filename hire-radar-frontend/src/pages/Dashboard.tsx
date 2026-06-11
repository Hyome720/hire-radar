import { CompanyForm } from '../components/CompanyForm'
import { Filters } from '../components/Filters'
import { PostingsTable } from '../components/PostingsTable'
import { useHireRadar } from '../hooks/useHireRadar'

export function Dashboard() {
  const radar = useHireRadar()
  const openCount = radar.postings.filter(({ posting }) => posting.status === 'OPEN').length
  const averageFit = radar.postings.length
    ? Math.round(radar.postings.reduce((sum, posting) => sum + posting.fitScore, 0) / radar.postings.length)
    : 0

  return (
    <main>
      <header className="topbar">
        <div>
          <p className="eyebrow">hire-radar</p>
          <h1>채용 공고 모니터링</h1>
        </div>
        <div className="stats">
          <div>
            <span>{radar.companies.length}</span>
            <small>기업</small>
          </div>
          <div>
            <span>{openCount}</span>
            <small>모집중</small>
          </div>
          <div>
            <span>{averageFit}%</span>
            <small>평균 적합도</small>
          </div>
        </div>
      </header>

      <Filters
        companies={radar.companies}
        query={radar.query}
        companyId={radar.companyId}
        openOnly={radar.openOnly}
        sortMode={radar.sortMode}
        loading={radar.loading}
        onQueryChange={radar.setQuery}
        onCompanyChange={radar.setCompanyId}
        onOpenOnlyChange={radar.setOpenOnly}
        onSortModeChange={radar.setSortMode}
        onScrape={radar.runScrape}
      />

      {radar.message && <p className="notice">{radar.message}</p>}

      <section className="content-grid">
        <PostingsTable companies={radar.companies} postings={radar.postings} />
        <CompanyForm onSubmit={radar.addCompany} />
      </section>
    </main>
  )
}
