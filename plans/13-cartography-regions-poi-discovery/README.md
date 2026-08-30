# 13 — Cartografia, Regiões, Locais e Descoberta

O Stage 13 transforma o mapa em uma camada de exploração RPG sem substituir o JourneyMap. O RPG Skill Tree mantém a autoridade sobre **regiões semânticas**, **locais de interesse (POIs)**, **conhecimento descoberto**, **rumores**, **áreas de busca** e **vínculos com quests**; o JourneyMap é um renderer/adaptador opcional.

O objetivo é permitir experiências como:

```text
Floresta de Valen
├── Vila de Oakheart [VISITADA]
├── Ruínas Antigas [RUMOR]
└── Torre do Mago [ÁREA APROXIMADA / quest ativa]
```

sem revelar automaticamente coordenadas secretas nem transformar o mapa em um `/locate` permanente.

## Cenário canônico

1. vários biomas compatíveis e contíguos são classificados como uma região semântica, por exemplo `FLORESTA`;
2. a região recebe `regionId` estável e nome persistente/localizado, por exemplo **Floresta de Valen**;
3. estruturas geradas ou observadas são classificadas como POIs, por exemplo `VILA`, `TORRE_MAGICA`, `RUINA`, `DUNGEON`;
4. o servidor conhece a posição física, mas o cliente recebe somente o nível de informação autorizado;
5. uma quest pode revelar apenas um rumor ou uma área aproximada;
6. ao explorar e descobrir o local, o estado avança e o JourneyMap passa a renderizar o marcador exato;
7. o mesmo local pode ser reutilizado por quests, Compêndio Natural, progressão e outros sistemas sem duplicar identidade.

## Separação de autoridade

```text
Mundo físico
├── RegionRecord / PoiRecord             WORLD_GLOBAL
│
Conhecimento do jogador
├── MapIntelState por bodyId             BODY_LOCAL por padrão
│
Apresentação
└── CartographyRenderer
    └── JourneyMapAdapter                CLIENT/OPTIONAL
```

### WORLD_GLOBAL

Pertencem ao mundo:

- identidade e geometria das regiões;
- posição e bounds físicos de POIs;
- estrutura de origem;
- estados físicos reais, quando aplicáveis;
- relações territoriais persistentes.

### BODY_LOCAL

Pertencem ao corpo ativo do Stage 12, por padrão:

- regiões conhecidas;
- POIs conhecidos;
- rumor/localização aproximada/exata;
- visitado/concluído;
- anotações de quest;
- intel recebida por mapa, NPC, livro, bússola ou outro sistema.

Isso preserva a semântica de “novo jogo” dos corpos. Um Corpo B recém-criado não deve herdar automaticamente todos os segredos cartográficos descobertos pelo Corpo A. Pode existir configuração explícita para conhecimento compartilhado por conta, mas não é o padrão canônico.

## Invariantes canônicas

1. **JourneyMap não é autoridade.** Remover ou atualizar JourneyMap não pode apagar regiões, POIs ou progresso de descoberta.
2. **Servidor controla informação sensível.** Coordenadas de POIs ocultos não podem ser enviadas ao cliente antes da descoberta autorizada.
3. **Sem varredura global.** Descoberta/indexação é incremental, orientada a chunks/eventos e bounded jobs.
4. **IDs persistentes.** `regionId` e `poiId` não mudam a cada reload.
5. **Classificação data-driven.** Biomas e estruturas conhecidos usam tags/IDs/adapters; conteúdo modded desconhecido recebe fallback seguro.
6. **Sem cheat por default.** Uma quest pode informar direção, região, círculo aproximado ou rumor sem revelar o ponto exato.
7. **Descoberta idempotente.** Reentrar em região/POI não duplica XP, rewards ou eventos.
8. **Estado físico != intel.** Destruir um local não equivale a “esquecer” sua existência.
9. **PT-BR first.** Nomes de categorias, estados, mensagens e UI próprias têm localização integral em português do Brasil.
10. **Integrações são fail-soft.** JourneyMap, Nature's Compass, Explorer's Compass ou futuros mapas podem faltar sem impedir servidor/startup.
11. **Compêndio é reutilizado.** Stage 13 consome catálogo/taxonomia do Stage 10 quando disponível; não cria um segundo inventário concorrente de biomas e estruturas.
12. **Stage 12 é respeitado.** Trocar de corpo reconcilia imediatamente os overlays para o conhecimento daquele corpo.
13. **Proveniência obrigatória.** Código ou assets derivados de terceiros só entram após auditoria de licença e registro em `SOURCES.md` / `THIRD_PARTY_NOTICES.md`.

## Referências técnicas e regras de licença

- **MapFrontiers**: referência útil para UX/overlays de fronteira. O upstream consultado usa licença MIT; trechos derivados só podem entrar com proveniência exata e preservação do aviso exigido pela licença.
- **Compass to Map**: referência funcional apenas. O upstream consultado é All Rights Reserved e proíbe copiar código ou partes dele para projeto público sem autorização escrita; a implementação deve ser clean-room.
- **JourneyMap API**: integração por API pública. Não copiar/embutir fonte ou class files da API fora do permitido pelos termos do TeamJM.

O contrato global de licenças/proveniência está em `plans/09-hardening-release/08-third-party-licenses-provenance.md`.

## Ordem causal

1. `01-domain-invariants-and-authority.md`
2. `02-biome-taxonomy-and-region-types.md`
3. `03-lazy-region-segmentation-and-boundaries.md`
4. `04-region-identity-naming-and-persistence.md`
5. `05-poi-structure-classification.md`
6. `06-discovery-intelligence-state-machine.md`
7. `07-journeymap-renderer-adapter.md`
8. `08-quest-objectives-and-search-areas.md`
9. `09-world-observation-and-discovery-hooks.md`
10. `10-body-local-map-state-stage12.md`
11. `11-compendium-and-progression-bridges.md`
12. `12-modded-content-and-map-adapters.md`
13. `13-data-network-admin-tools.md`
14. `14-migration-fallback-and-recovery.md`
15. `15-testing-performance-hardening.md`

## Gate de conclusão

O estágio só fecha quando for possível, em dedicated server e sem vazamento de informação:

```text
explorar biomas
→ formar região persistente “Floresta ...”
→ receber quest sobre uma Torre do Mago ainda desconhecida
→ mapa mostrar apenas área aproximada/rumor
→ explorar a área
→ descobrir o POI real
→ marcador exato aparecer
→ quest avançar uma única vez
→ trocar para Corpo B novo
→ segredos do Corpo A desaparecerem do renderer
→ voltar ao Corpo A
→ conhecimento original retornar exatamente
→ save/reload preservar tudo
```

Também devem existir testes para conteúdo modded desconhecido, ausência de JourneyMap, atualização de taxonomia, remoção de markers obsoletos e carga elevada de regiões/POIs.