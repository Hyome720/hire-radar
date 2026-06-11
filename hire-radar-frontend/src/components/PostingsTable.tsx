import type { Company, PostingSummary } from '../services/api'

type Props = {
  companies: Company[]
  postings: PostingSummary[]
}

export function PostingsTable({ companies, postings }: Props) {
  const companyName = (id: number) => companies.find((company) => company.id === id)?.name ?? '-'

  return (
    <div className="table-wrap">
      <table>
        <thead>
          <tr>
            <th>회사</th>
            <th>공고명</th>
            <th>상태</th>
            <th>등록일</th>
            <th>마감일</th>
            <th>적합도</th>
            <th>링크</th>
          </tr>
        </thead>
        <tbody>
          {postings.map(({ posting, fitScore, fitReasons }) => (
            <tr key={posting.id}>
              <td>{companyName(posting.companyId)}</td>
              <td>
                <strong>{posting.title}</strong>
                <small>{fitReasons.join(', ')}</small>
              </td>
              <td>
                <span className={posting.status === 'OPEN' ? 'pill open' : 'pill'}>{posting.status}</span>
              </td>
              <td>{posting.postedDate || '-'}</td>
              <td>{posting.deadline || '-'}</td>
              <td>
                <meter min="0" max="100" value={fitScore} />
                <b>{fitScore}%</b>
              </td>
              <td>
                <a href={posting.url} target="_blank" rel="noreferrer">
                  열기
                </a>
              </td>
            </tr>
          ))}
          {postings.length === 0 && (
            <tr>
              <td colSpan={7} className="empty">
                표시할 공고가 없습니다.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  )
}
