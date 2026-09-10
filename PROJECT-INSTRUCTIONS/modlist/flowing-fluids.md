# FlowingFluids — 1.0.6

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d569db9f0db81458887f051db4c7cd9  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** FlowingFluids
- **Arquivo JAR:** `flowing_fluids-1.0.6-1.21-neoforge.jar`
- **Versão 1.21.1:** `1.0.6`
- **Categoria:** não definida
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/flowing-fluids
- **Função:** Substitui a dinâmica vanilla por fluidos com física mais realista e majoritariamente finita, níveis parciais, drenagem/refill configuráveis e integração específica com pipes, hose pulleys e water wheels do Create.
- **Dependências:** NeoForge 1.21.1. Integração comportamental relevante com Create 6.0.10 e Sable; `sable_flowing_fluids_compat-1.0.2.jar` também está fisicamente presente no pack.
- **Compatibilidade/Riscos:** Altera a semântica global de fluidos e pode afetar qualquer máquina/worldgen que pressuponha source blocks vanilla. 1.0.6 corrige launch crash com Sable/Create Aeronautics, config NeoForge e infinite-water check de Create pipes. Riscos: drenagem inesperada, chunk-border state, pumps/pipes, worldgen water bodies e performance.
- **Sobreposição:** É authority de comportamento/propagação de fluidos no mundo; não substitui storage/piping de Create ou outros mods. Cruza diretamente com worldgen hídrico, pumps, water wheels, agricultura e Sable.
- **Observações:** JAR físico `flowing_fluids-1.0.6-1.21-neoforge.jar`, mod id `flowing_fluids`, runtime 1.0.6. Upstream ainda descreve o projeto como em desenvolvimento; server é necessário e cliente é recomendado para representação/interação completa.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Flowing Fluids 1.0.6 + changelog 1.0.6 + integração Create documentada.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — ficha mínima substituída por dossiê técnico da build física 1.0.6; fluid physics, finite sources, Create/Sable compatibility, configs, lifecycle e riscos catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🌊 **ESCOPO CANÔNICO.** Runtime físico: `flowing_fluids-1.0.6-1.21-neoforge.jar`, mod id `flowing_fluids`, versão `1.0.6`. Flowing Fluids muda a semântica do próprio **fluido no mundo**: líquidos passam a fluir com níveis e volume majoritariamente finitos. Isso é distinto de storage/pipes de outros mods.

## 1. Física e volume
O projeto permite que fluidos escorram por declives, formem pools, encham aquedutos e causem flooding. Source blocks deixam de ser a única unidade relevante: níveis parciais podem mover-se e conservar volume conforme as regras do mod.

Oceans, rivers e swamps podem se comportar como grandes reservas/refill sources conforme configuração, mas não devem ser tratados como infinitos por hardcode de integração externa.

## 2. Interações de gameplay
A documentação expõe opções para agricultura e uso manual: farmland pode consumir nível de água, breeding pode consumir água próxima, bottles podem retirar níveis e buckets podem captar volumes graduais. Evaporation/refill também podem depender de dimensão, chuva e biome.

Essas superfícies são configuráveis; a ficha não presume que todos os toggles estejam ativos sem ler a config real do pack.

## 3. Fluids modded e displacement
Fluidos modded são genericamente suportados quando compatíveis com o sistema, com blacklist para exceções. O mod também lida com displacement por blocos e pumping via pistons. Qualquer integração com fluidos não vanilla precisa validar tags/handlers e conservação de volume.

## 4. Create 6 compatibility
Flowing Fluids possui integração específica com Create. Pipes e Hose Pulleys podem mover fluido de forma finita e existe opção para tratar fluidos como fontes infinitas. Water Wheels podem exigir fluxo real ou condições alternativas configuráveis; o default documentado aceita flow ou river em certas condições.

A build 1.0.6 corrigiu Create pipes que ainda usavam o check vanilla de água infinita. O pack usa Create 6.0.10, logo esse caminho é regression gate obrigatório.

## 5. Sable / Aeronautics
A 1.0.6 corrige launch crash quando Sable/Create Aeronautics estava instalado. O pack também contém `sable_flowing_fluids_compat-1.0.2.jar`, portanto fluid state em sublevels/ships deve ser testado explicitamente. Não assumir que o fix de boot prova correção de toda simulação em estruturas móveis.

## 6. Configuração e lifecycle
Comandos documentados incluem `/flowing_fluids settings` e `/flowing_fluids help`. A 1.0.6 corrige carregamento/atualização de config no NeoForge e impede o auto-performance de sobrescrever config salva.

Servidor é necessário para a lógica autoritativa; cliente é recomendado para visual/interação completa. Validar restart, chunk unload/load, fronteira de chunks, dimension transfer, `/reload` quando aplicável e mudança de config.

## 7. Remoção/disable
O upstream documenta que full source blocks tendem a permanecer quando o sistema é removido/desabilitado, enquanto níveis parciais podem desaparecer ou voltar ao comportamento vanilla quando atualizados. Portanto desinstalação em mundo existente exige backup e inspeção de áreas com fluidos parciais.

## 8. Riscos
1. **Volume duplication/loss:** dois handlers consomem/produzem o mesmo nível.
2. **Chunk-border divergence:** fluxo fica stale ao atravessar chunks unloaded.
3. **Worldgen assumptions:** lakes/rivers criados por outros providers drenam ou refillam de forma inesperada.
4. **Create mismatch:** pipe/hose/water-wheel usa semântica vanilla em parte do pipeline.
5. **Sable sublevels:** transform/unload duplica ou perde fluid state.
6. **Performance:** grandes floods e atualização de muitos níveis ampliam custo de ticks.

## 9. Boundary para quests/perks
Fluir água, drenar uma área ou atualizar níveis por tick não é evento de Mastery por si só. Qualquer milestone deve partir de uma ação causal deduplicável e server-authoritative, nunca da simples presença visual de fluido.

## 10. Matriz de testes
- [ ] Dedicated server inicia com Flowing Fluids 1.0.6 + Create 6.0.10 + Sable stack.
- [ ] Volume se conserva em queda, pool e transferência entre níveis.
- [ ] Buckets/bottles respeitam volume configurado sem dupe.
- [ ] Pipes/Hose Pulleys não recriam fonte infinita indevida.
- [ ] Water Wheels seguem a regra configurada.
- [ ] Rivers/oceans do worldgen atual não drenam de forma catastrófica.
- [ ] Chunk unload/reload preserva estado coerente.
- [ ] Sublevels Sable não duplicam/perdem fluido.
- [ ] Restart mantém config e não reativa auto-tuning indesejado.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limitação
- Modlist física: `flowing_fluids-1.0.6-1.21-neoforge.jar`; compat Sable 1.0.2 também presente.
- Upstream oficial: finite-fluid physics, Create compatibility e fixes 1.0.6 descritos acima.
- Config efetiva do pack não foi lida nesta etapa; features opcionais permanecem fail-closed até inspeção/runtime QA.