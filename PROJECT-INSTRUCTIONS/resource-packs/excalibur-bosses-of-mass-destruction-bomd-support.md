# Excalibur | Bosses of Mass Destruction (BOMD) Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db815ab9a8c4b345002272
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur BOMD Support 1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur BOMD Support 1.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Bosses of Mass Destruction `1.3.3`. Como o support v1.1 é anterior ao target físico, assets posteriores permanecem superfície de regressão, não compatibilidade presumida.

## Propriedades do banco

- **Mod:** Excalibur | Bosses of Mass Destruction (BOMD) Support
- **Arquivo JAR:** `Excalibur BOMD Support 1.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Mobs
- **Função:** Support pack visual amplo para Bosses of Mass Destruction, cobrindo mobs/bosses, items, particles, boss bars e UI no estilo Excalibur.
- **Dependências:** Uso visual pretendido: Excalibur + Bosses of Mass Destruction. Stack físico atual: BOMD 1.3.3. Conteúdo client-side; gameplay permanece no BOMD.
- **Sobreposição:** Sobreposição visual possível com Boss Refreshed, Mobs Refreshed/Fresh Animations e GUI/particle packs. BOMD continua único owner de combat/AI/loot.
- **Compatibilidade/Riscos:** A v1.1 é de 30/12/2025 e o alvo físico é BOMD 1.3.3; há risco de assets mais novos sem cobertura. Também pode colidir com Boss Refreshed, Mobs Refreshed/Fresh Animations e GUI/particle packs por asset path.
- **Observações:** Arquivo instalado `Excalibur BOMD Support 1.1.zip`. Upstream confirma transformação de mobs/bosses, items, particles, boss bars e UI; contagem exata de assets não foi inferida.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur BOMD Support v1.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-bosses-of-mass-destruction-bomd
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; BOMD 1.3.3, mobs/items/particles/boss bars/UI, drift temporal, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur BOMD Support 1.1.zip`, versão `1.1`, Release para Minecraft 1.21.1. O projeto aplica uma transformação visual ampla a Bosses of Mass Destruction no estilo Excalibur.

## 1. Papel e authority
Excalibur | Bosses of Mass Destruction (BOMD) Support altera apenas apresentação. **Bosses of Mass Destruction** continua authority de bosses, mobs, AI, combat, damage, projectiles, loot, structures e progression.
Boss bars, particles e UI retexturizados continuam sendo feedback visual; não controlam health, phase ou resolução de combate.

## 2. Cobertura confirmada
O upstream descreve a v1.1 como transformação visual em escala ampla incluindo:
- mobs e bosses;
- items;
- particles;
- boss bars;
- UI.
Essas categorias são confirmadas pelo projeto. A ficha não inventa número de assets, entities ou caminhos internos não publicados.

## 3. Stack físico atual
A modlist mantém Bosses of Mass Destruction `1.3.3` para NeoForge 1.21.1. O resource pack v1.1 foi publicado em **30/12/2025**.
Como o target mod físico é posterior, conteúdo novo ou alterações de asset introduzidas em BOMD 1.3.3 podem exigir fallback ou permanecer no visual original. Isso é regression surface, não incompatibilidade provada.

## 4. Load order
O projeto é recomendado para uso junto ao Excalibur. Para que seus overrides de BOMD prevaleçam, deve ficar acima do Excalibur base. Outros retextures do mesmo mod podem substituir assets conforme prioridade.

## 5. Boss bars, particles e UI
Esses elementos são client-facing. A aparência da boss bar não é authority de HP/phase; particle count/texture não define hit registration; UI não altera inventory ou boss mechanics.
Qualquer integração própria deve continuar lendo state do BOMD, nunca inferir gameplay pelo asset exibido.

## 6. Client e resource reload
Ativar/remover/reordenar deve provocar resource reload e alterar exclusivamente textures/models/GUI/particles. Bosses existentes no mundo não devem mudar entity state, loot tables ou health.

## 7. Sobreposição
Pode colidir com Boss Refreshed, Mobs Refreshed/Fresh Animations ou outros packs caso toquem entidades/assets do BOMD. A coexistência precisa ser resolvida por asset/model priority concreta.
Não presumir que um pack de animação seja substituto de um retexture completo.

## 8. Riscos
1. BOMD 1.3.3 ter assets posteriores à v1.1.
2. Boss/entity ficar parcialmente Excalibur e parcialmente default.
3. Boss bar ou UI ser sobrescrita por outro GUI pack.
4. Particle texture ser substituída por outro resource pack/shader integration.
5. Load order impedir os overrides.
6. Resource reload deixar cache visual stale.

## 9. Matriz de testes
- [ ] Testar os bosses do BOMD fisicamente disponíveis no pack.
- [ ] Conferir mobs/bosses e items no estilo Excalibur.
- [ ] Conferir boss bars e UI durante encounter real.
- [ ] Conferir particles sem missing sprites.
- [ ] Verificar conteúdo introduzido/alterado em BOMD 1.3.3 para fallback visual.
- [ ] Testar prioridade com Boss Refreshed/Mobs Refreshed se ambos tocarem BOMD.
- [ ] Resource reload/relog sem missing assets.

Nenhum teste foi marcado como aprovado.

## 10. Evidências e limite
- captura CurseForge do perfil: `Excalibur BOMD Support 1.1.zip`;
- modlist física: BOMD `1.3.3`;
- CurseForge oficial: v1.1 para Minecraft 1.21.1 e transformação de mobs/bosses/items/particles/boss bars/UI.
O ZIP não foi inventariado internamente; compatibilidade integral com cada asset de BOMD 1.3.3 permanece sujeita a QA.

> Boundary canônico: **BOMD controla boss state e combate; o support pack controla somente apresentação visual, incluindo boss bars/particles/UI**.
