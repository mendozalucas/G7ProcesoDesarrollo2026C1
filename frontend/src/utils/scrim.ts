export function parseRegion(region: string): { servidor: string; zona: string } {
  const slash = region.indexOf('/');
  if (slash >= 0) {
    return { servidor: region.slice(0, slash), zona: region.slice(slash + 1) };
  }
  return { servidor: region, zona: '' };
}

export function formatRegion(region: string): string {
  const { servidor, zona } = parseRegion(region);
  return zona ? `${servidor}/${zona}` : servidor;
}
